package com.sandbox.service;

import com.sandbox.entity.Wallet;

import java.util.Optional;

public interface WalletService {
    void deleteById(Long id);

    Optional<Wallet> findById(Long id);
}
