package com.sandbox.repository;

import com.sandbox.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet save(Wallet wallet);
}
