package com.sandbox.service;

import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.TransactionsViewParametersDto;

import java.util.List;

import com.sandbox.dto.TransactionDto;

public interface TransactionService {

    void deleteById(Long id, String username);

    List<TransactionDto> viewTransactionsList(TransactionsViewParametersDto searchParameters);

    Long createTransaction(TransactionDto transactionDto, String username);
}
