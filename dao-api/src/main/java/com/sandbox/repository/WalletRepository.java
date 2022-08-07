package com.sandbox.repository;

import com.sandbox.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet save(Wallet wallet);

    void deleteById(Long id);
}
