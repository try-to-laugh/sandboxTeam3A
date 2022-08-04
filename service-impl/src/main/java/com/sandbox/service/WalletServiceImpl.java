package com.sandbox.service;

import com.sandbox.dto.WalletDto;
import com.sandbox.entities.Wallet;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{
    private final WalletRepository walletRepository;
    @Override
    public List<WalletDto> allWallet() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletDto> walletDtos = new ArrayList<>();
        wallets.forEach(walletEntity -> walletDtos.add(WalletDto.getData(walletEntity)));
        return walletDtos;
    }
}
