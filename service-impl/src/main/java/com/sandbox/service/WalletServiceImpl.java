package com.sandbox.service;

import com.sandbox.entity.User;
import com.sandbox.entity.Wallet;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public void deleteById(Long id) {
        Wallet foundedWallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id));

        if (foundedWallet.isDefault()) {
            changeDefaultWallet(id);
        }
        walletRepository.deleteById(id);
    }

    private void changeDefaultWallet(long deletedWalletId) {
        Wallet wallet = new Wallet();
        User walletOwner = wallet.getUser();
        Optional<Wallet> newDefaultWalletOptional = walletOwner.getWallets().stream().filter(x -> x.getId() != deletedWalletId)
                .max(Comparator.comparing(Wallet::getBalance));
        if (newDefaultWalletOptional.isEmpty()) {
            return;
        }
        // todo Change wallet
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }
}
