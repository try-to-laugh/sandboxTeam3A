package com.sandbox.service;


import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;

    @Override
    public Wallet updateWalletById(Long id, WalletDto requestWallet) {
        Wallet responseWallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("wallet with  id=" + id + " not found'"));
        responseWallet.setName(requestWallet.getName());
        responseWallet.setCurrency(requestWallet.getCurrency());
        if(requestWallet.getIsDefault() == "true"){
            responseWallet.setDefault(true);
        } else {
            responseWallet.setDefault(false);
        }

        walletRepository.save(responseWallet);

        return responseWallet;
    }
}
