package com.sandbox.service;

import com.sandbox.dto.TransactionDto;

import java.util.Optional;

public interface TransactionService {
    void deleteById(Long id, String username);

    Long createTransaction(TransactionDto transactionDto, String username);

    Optional<TransactionDto> findById(Long transactionId);
}
