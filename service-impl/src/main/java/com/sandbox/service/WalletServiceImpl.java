package com.sandbox.service;

import com.sandbox.entity.User;
import com.sandbox.entity.Wallet;
import com.sandbox.exceptionHandler.exceptions.WalletNotFoundException;
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
    Optional<Wallet> wallet;

    @Override
    public void deleteById(Long id) {
        wallet = Optional.ofNullable(walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id)));

        if (wallet.get().isDefault()) {
            changeDefaultWallet(id);
        }
        walletRepository.deleteById(id);
    }

    private void changeDefaultWallet(long deletedWalletId) {
        User walletOwner = wallet.get().getUser();
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
