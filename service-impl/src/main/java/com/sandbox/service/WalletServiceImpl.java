package com.sandbox.service;

import com.sandbox.entity.Wallet;
import com.sandbox.repository.WalletRepository;

public class WalletServiceImpl implements WalletService {
    private WalletRepository walletRepository;
    private Wallet wallet;

    @Override
    public void deleteById(Long id) {
        wallet = walletRepository.getById(id);
        if (!wallet.isDefault()) {
            walletRepository.deleteById(id);
        }
    }
}
