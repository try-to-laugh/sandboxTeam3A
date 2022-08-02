package com.sandbox.service;

import com.sandbox.dto.WalletDto;
import com.sandbox.entities.Wallet;

import java.util.Optional;

public interface WalletService {

    Wallet createWallet(WalletDto walletDto);
}
