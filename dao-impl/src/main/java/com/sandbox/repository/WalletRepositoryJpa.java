package com.sandbox.repository;

import com.sandbox.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepositoryJpa extends JpaRepository<Wallet, Long> {

    @Query("select w from Wallet w where w.defaultWallet = ?1 and w.userId = ?2")
    Optional<Wallet> findByDefault(Boolean status, Long walletOwnerId);

    void deleteById(Long id);
}
