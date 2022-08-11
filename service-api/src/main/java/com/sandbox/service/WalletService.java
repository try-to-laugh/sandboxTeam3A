package com.sandbox.service;

import com.sandbox.dto.WalletDto;

public interface WalletService {

    WalletDto updateWalletById(Long id, WalletDto requestWallet, String userName);


    void deleteById(Long id, String username);
}
