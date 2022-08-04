package com.sandbox.service;

import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;

public interface WalletService {

    WalletResponseDto updateWalletById(Long id, WalletRequestDto requestWallet);

}
