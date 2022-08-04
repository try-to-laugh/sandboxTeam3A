package com.sandbox.repository;

import com.sandbox.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepositoryJpa extends JpaRepository<Wallet, Long> {
    void deleteById(Long id);

}
