package com.sandbox.service;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;

public interface WalletService {

    Wallet updateWalletById(Long id, WalletDto requestWallet);

}
