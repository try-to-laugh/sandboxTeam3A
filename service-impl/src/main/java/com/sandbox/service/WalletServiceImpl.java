package com.sandbox.service;

import com.sandbox.entity.Wallet;
import com.sandbox.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private WalletRepository walletRepository;

    @Override
    public void deleteById(Long id) {

    }
}
