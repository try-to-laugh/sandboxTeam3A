package com.sandbox.repository;

import com.sandbox.entity.Wallet;

public interface WalletRepository {//TODO might to use JpaRepository

    void deleteById(Long id);
    Wallet getById(Long id);
}
