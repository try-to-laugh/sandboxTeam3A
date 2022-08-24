package com.sandbox.service;

import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.WalletDto;

import java.util.List;

public interface WalletService {

    WalletDto updateWalletById(Long id, WalletDto requestWallet, String username);

    WalletDto getWalletById(Long walletId, String username);

    void deleteById(Long id, String username);

    Long createWallet(WalletDto walletDto, String username);

    List<WalletDto> getWallets(String username);

    Long save(WalletDto walletDto);

    void updateBalance(TransactionDto transaction, WalletDto wallet, boolean rollback);

    long countTransactionByWalletId (Long walletId);
}
