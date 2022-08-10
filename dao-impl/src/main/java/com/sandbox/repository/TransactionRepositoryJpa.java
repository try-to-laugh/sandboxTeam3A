package com.sandbox.repository;

import com.sandbox.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepositoryJpa extends JpaRepository<Transaction, Long> {
}
