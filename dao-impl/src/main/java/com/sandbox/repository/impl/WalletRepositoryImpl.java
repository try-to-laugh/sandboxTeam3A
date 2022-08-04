package com.sandbox.repository.impl;

import com.sandbox.repository.WalletRepository;
import com.sandbox.repository.WalletRepositoryJpa;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepository {
    private final WalletRepositoryJpa walletRepositoryJpa;

    @Override
    public void deleteById(Long id) {
        walletRepositoryJpa.deleteById(id);
    }
}
