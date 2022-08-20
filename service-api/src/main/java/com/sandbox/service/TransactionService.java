package com.sandbox.service;

import com.sandbox.dto.TransactionDto;

public interface TransactionService {
    void deleteById(Long id, String username);

    Long createTransaction(TransactionDto transactionDto, String username);
}
