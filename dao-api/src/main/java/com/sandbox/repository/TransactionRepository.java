package com.sandbox.repository;

import com.sandbox.dto.TransactionDto;

import java.util.Optional;

public interface TransactionRepository {

    Long save(TransactionDto transactionDto);

    void deleteById(Long id);

    Optional<TransactionDto> findById(Long id);
}
