package com.sandbox.repository;

import com.sandbox.dto.WalletDto;

import java.util.Optional;

public interface WalletRepository {
    Optional<WalletDto> findById(Long id);

    void deleteById(Long id);
}
