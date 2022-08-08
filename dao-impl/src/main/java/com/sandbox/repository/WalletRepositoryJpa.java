package com.sandbox.repository;

import com.sandbox.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepositoryJpa extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByDefaultStatus(Boolean status);

    void deleteById(Long id);
}
