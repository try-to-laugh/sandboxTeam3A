package com.sandbox.service;

import com.sandbox.dto.WalletDto;

public interface WalletService {

    WalletDto updateWalletById(Long id, WalletDto requestWallet, String userName);

    WalletDto getWalletById(Long walletId);

    void deleteById(Long id, String username);

    Long createWallet(WalletDto walletDto, String username);
}
