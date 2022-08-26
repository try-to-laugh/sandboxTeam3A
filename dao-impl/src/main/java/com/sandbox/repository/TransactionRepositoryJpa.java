package com.sandbox.repository;

import com.sandbox.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface TransactionRepositoryJpa extends JpaRepository<Transaction, Long> {

    void deleteById(Long id);

    Optional<Transaction> findById(Long id);

    List<Transaction> findAllByWalletId(Long walletId, Pageable transactionList);

    List<Transaction> findAllByWalletIdAndTypeId(Long walletId, Long TypeId, Pageable transactionList);

    @Query("select distinct t from Transaction t join Wallet w on t.walletId = w.id " +
            "where w.userId = :userId and t.typeId = :typeId")
    List<Transaction> findAllByTypeId(@Param("typeId") Long typeId, Pageable transactionList, @Param("userId") Long userId);

    @Query("select distinct t from Transaction t join Wallet w on t.walletId = w.id where w.userId = :userId")
    List<Transaction> findAllTransactions(Pageable transactionList, @Param("userId") Long userId);

    Optional<Transaction> findByCategoryId(Long id);
}
