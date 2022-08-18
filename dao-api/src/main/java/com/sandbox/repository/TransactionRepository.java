package com.sandbox.repository;

import com.sandbox.dto.TransactionDto;

import java.util.Optional;

public interface TransactionRepository {
    void deleteById(Long id);

    Optional<TransactionDto> findById(Long id);
}
