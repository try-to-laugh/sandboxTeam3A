package com.sandbox.repository.impl;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.mapper.WalletMapper;
import com.sandbox.repository.WalletRepository;
import com.sandbox.repository.WalletRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepository {
    private final WalletRepositoryJpa walletRepositoryJpa;
    private final WalletMapper walletMapper;

    @Override
    public Optional<WalletDto> findById(Long id) {
        Optional<Wallet> wallet = walletRepositoryJpa.findById(id);
        return wallet.map(walletMapper::toWalletDto);
    }

    @Override
    public void deleteById(Long id) {
        walletRepositoryJpa.deleteById(id);
    }
}
