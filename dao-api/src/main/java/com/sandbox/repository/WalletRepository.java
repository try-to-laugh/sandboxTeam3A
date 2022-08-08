package com.sandbox.repository;

import com.sandbox.dto.WalletDto;

import java.util.Optional;

public interface WalletRepository {

    Optional<WalletDto> findById(Long id);

    Optional<WalletDto> findByStatus(Boolean walletStatus);

    void save(WalletDto walletDto);

    void deleteById(Long id);

}
