package com.sandbox.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository {
    void deleteById(Long id);
}
