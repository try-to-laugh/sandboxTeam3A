package com.sandbox.repository.impl;

import com.sandbox.dto.TransactionDto;
import com.sandbox.entity.Transaction;
import com.sandbox.mapper.TransactionMapper;
import com.sandbox.repository.TransactionRepository;
import com.sandbox.repository.TransactionRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {
    private final TransactionRepositoryJpa transactionRepositoryJpa;
    private final TransactionMapper transactionMapper;

    @Override
    public void deleteById(Long id) {
        transactionRepositoryJpa.deleteById(id);
    }

    @Override
    public Optional<TransactionDto> findById(Long id) {
        Optional<Transaction> transaction = transactionRepositoryJpa.findById(id);
        return transaction.map(transactionMapper::toTransactionDto);
    }
}
