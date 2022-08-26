package com.sandbox.repository;

import com.sandbox.dto.TransactionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Long save(TransactionDto transactionDto);

    void deleteById(Long id);

    Optional<TransactionDto> findById(Long id);

    List<TransactionDto> findTransactions(String transactionType, Pageable pageable, Long userId);

    List<TransactionDto> findAllTransactions(Pageable pageable, Long userId);

    List<TransactionDto> findWalletTransactions(Long walletId, String transactionType, Pageable pageable);

    List<TransactionDto> findAllWalletTransactions(Long walletId, Pageable pageable);

    Optional<TransactionDto> findByCategoryId(Long id);

}
