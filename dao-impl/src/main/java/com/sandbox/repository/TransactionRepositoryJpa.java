package com.sandbox.repository;

import com.sandbox.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepositoryJpa extends JpaRepository<Transaction, Long> {
    void deleteById(Long id);

    Optional<Transaction> findById(Long id);
}
