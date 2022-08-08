package com.sandbox.service;

import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
