package com.sandbox.repository.impl;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.exception.BudgetRuntimeException;
import com.sandbox.mapper.WalletMapper;
import com.sandbox.repository.WalletRepository;
import com.sandbox.repository.WalletRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Optional<WalletDto> findByStatus(Boolean walletStatus, Long walletOwnerId) {
        Optional<Wallet> wallet = walletRepositoryJpa.findByDefault(walletStatus, walletOwnerId);
        return wallet.map(walletMapper::toWalletDto);
    }

    @Override
    public void save(WalletDto walletDto) {
        try {
            Wallet savedWallet = walletMapper.toWallet(walletDto);
            walletRepositoryJpa.saveAndFlush(savedWallet);
        } catch (DataIntegrityViolationException ex) {
            throw new BudgetRuntimeException("A wallet with such a name and currency already exists. "
                    + "Please, change the name", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        walletRepositoryJpa.deleteById(id);
    }
}
