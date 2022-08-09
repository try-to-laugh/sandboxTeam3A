package com.sandbox.service;

import com.sandbox.dto.WalletDto;

public interface WalletService {

    WalletDto updateWalletById(Long id, WalletDto requestWallet);


    void deleteById(Long id);

    WalletDto getWalletById(Long walletId);
}
