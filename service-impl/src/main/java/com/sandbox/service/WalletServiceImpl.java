package com.sandbox.service;

import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.exception.WalletWithSameNameAndCurrencyExist;
import com.sandbox.mapper.WalletMapper;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Override
    public void deleteById(Long id) {
        WalletDto walletWhichWantToDelete = walletRepository.findById(id)
                .orElseThrow(() ->
                        new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id));

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
            LOG.info("User had only one wallet and it was default");
            Optional<WalletDto> newDefaultWalletOptional = walletOwner.getWallets().stream()
                    .max(Comparator.comparing(WalletDto::getBalance));
            newDefaultWalletOptional.ifPresent(wallet -> wallet.setDefault(true));
            LOG.info("Default wallet successfully changed");
        }
    }


    public WalletResponseDto updateWalletById(Long walletId, WalletRequestDto walletRequestDto) {
        try {

            WalletDto updatedWallet = walletRepository.findById(walletId)
                    .orElseThrow(() -> new WalletNotFoundException("wallet with  id = " + walletId + " not found'"));

            Optional<WalletDto> defaultStatusWalletCheck = walletRepository.findByStatus(walletRequestDto.getDefault());
            if (defaultStatusWalletCheck.isPresent()) {
                WalletDto makeFalseWallet = defaultStatusWalletCheck.get();
                makeFalseWallet.setDefault(false);
                walletRepository.save(makeFalseWallet);
            }

            WalletDto walletChanges = walletMapper.fromWalletRequestDtoToWalletDto(walletRequestDto);
            updatedWallet.setName(walletChanges.getName());
            updatedWallet.setCurrency(walletChanges.getCurrency());
            updatedWallet.setDefault(walletChanges.isDefault());

            walletRepository.save(updatedWallet);

            return walletMapper.fromWalletDtoToWalletResponseDto(updatedWallet);
        } catch (DataIntegrityViolationException ex) {
            throw new WalletWithSameNameAndCurrencyExist("A wallet with such a name and currency already exists. Please, change the name", ex);
        }

    }

}
