package com.sandbox.service;

import com.sandbox.model.WalletRequestDto;

public interface WalletService {

    Long createWallet(WalletRequestDto walletRequestDto);

    void deleteById(Long id);
}
