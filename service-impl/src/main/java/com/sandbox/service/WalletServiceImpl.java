package com.sandbox.service;

import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.UserDetailsImpl;
import com.sandbox.entity.User;
import com.sandbox.entity.Wallet;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.exception.WalletWithSameNameAndCurrencyExist;
import com.sandbox.mapper.WalletMapper;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.repository.UserRepository;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper;

    @Override
    public Long createWallet (WalletRequestDto walletRequestDto) {
        UserDetailsImpl userDetails =(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).get();
        Set<Wallet> userWallets = user.getWallets();
        if (userWallets.isEmpty() ||
                userWallets.stream()
                        .map(wallet -> wallet.isDefaultStatus())
                        .noneMatch(status -> status.equals(true))) {
            walletRequestDto.setDefault(true);
        } else if (walletRequestDto.getDefault() &&
                userWallets.stream()
                        .map(wallet -> wallet.isDefaultStatus())
                        .anyMatch(status -> status.equals(true))
                        )
        {
            userWallets.forEach(wallet -> wallet.setDefaultStatus(false));
        }
        Wallet walletToCreate = walletMapper.fromWalletRequestDtoToWallet(walletRequestDto);
        walletToCreate.setDefaultStatus(walletRequestDto.getDefault());
        walletToCreate.setUser(user);
        if(user.getWallets().contains(walletToCreate)) {
            throw new WalletWithSameNameAndCurrencyExist("Such wallet already exists, please choose another currency or change the name of wallet");
        }
        return walletRepository.save(walletToCreate).getId();
    }

    @Override
    public void deleteById(Long id) {
        WalletDto walletWhichWantToDelete = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id));

        if (walletWhichWantToDelete.isDefault()) {
            UserDto walletOwner = walletWhichWantToDelete.getUser();
            walletRepository.deleteById(id);
            changeDefaultWallet(walletOwner);
        }

        walletRepository.deleteById(id);

        LOG.info("The wallet was successfully deleted");
    }

    private void changeDefaultWallet(UserDto walletOwner) {
        if (!walletOwner.getWallets().isEmpty()) {
            Optional<WalletDto> newDefaultWalletOptional = walletOwner.getWallets().stream()
                    .max(Comparator.comparing(WalletDto::getBalance));
            newDefaultWalletOptional.ifPresent(wallet -> wallet.setDefault(true));
            LOG.info("Default wallet successfully changed");
        }
        LOG.info("User had only one wallet and it was default");
    }
}
