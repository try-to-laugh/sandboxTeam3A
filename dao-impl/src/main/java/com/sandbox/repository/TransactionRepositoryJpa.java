package com.sandbox.repository;

import com.sandbox.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface TransactionRepositoryJpa extends JpaRepository<Transaction, Long> {

    void deleteById(Long id);

    Optional<Transaction> findById(Long id);

    List<Transaction> findAllByWalletId(Long walletId, Pageable transactionList);

    List<Transaction> findAllByWalletIdAndTypeId(Long walletId, Long TypeId, Pageable transactionList);

    List<Transaction> findAllByTypeId(Long typeId, Pageable transactionList);

    @Query("select t from Transaction t")
    List<Transaction> findAllTransactions(Pageable transactionList);

    Optional<Transaction> findByCategoryId(Long id);
}
