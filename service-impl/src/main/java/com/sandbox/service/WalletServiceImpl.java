package com.sandbox.service;

import com.sandbox.entities.Wallet;
import com.sandbox.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet saveWallet(Wallet wallet){
        return walletRepository.save(wallet);
    }
}
