package com.sandbox.service;

import com.sandbox.entity.User;
import com.sandbox.entity.Wallet;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.WalletRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);
    private final WalletRepositoryJpa walletRepository;

    @Override
    public void deleteById(Long id) {
        Wallet walletWhichWantToDelete = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id));

        if (walletWhichWantToDelete.isDefault()) {
            User walletOwner = walletWhichWantToDelete.getUser();
            walletRepository.deleteById(id);
            changeDefaultWallet(walletOwner);
        }

        LOG.info("The wallet was successfully deleted");
    }

    private void changeDefaultWallet(User walletOwner) {
        if (!walletOwner.getWallets().isEmpty()) {
            Optional<Wallet> newDefaultWalletOptional = walletOwner.getWallets().stream()
                    .max(Comparator.comparing(Wallet::getBalance));
            newDefaultWalletOptional.ifPresent(wallet -> wallet.setDefault(true));
            LOG.info("Default wallet successfully changed");
        }
        LOG.info("User had only one wallet and it was default");
    }

}
