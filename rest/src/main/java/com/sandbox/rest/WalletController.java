package com.sandbox.rest;

import com.sandbox.entities.Wallet;
import com.sandbox.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/budget/wallet")
public class WalletController {

    @Autowired
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        return new ResponseEntity<Wallet>(walletService.saveWallet(wallet), HttpStatus.OK);
    }
}
