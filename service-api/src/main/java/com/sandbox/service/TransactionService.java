package com.sandbox.service;

import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.TransactionsViewParametersDto;

import java.util.List;

import com.sandbox.dto.TransactionDto;

import java.util.Optional;

public interface TransactionService {

    void deleteById(Long id, String username);

    List<TransactionDto> viewTransactionsList(TransactionsViewParametersDto searchParameters, String username);

    Long createTransaction(TransactionDto transactionDto, String username);

    Optional<TransactionDto> findById(Long transactionId);

    TransactionDto updateTransactionById(Long transactionId, TransactionDto transactionDto, String username);
}
