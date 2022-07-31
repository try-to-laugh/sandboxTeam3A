package com.sandbox.rest;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;


    @PutMapping("/{id}")
    public ResponseEntity<Wallet> updateWalletById(@PathVariable Long id, @RequestBody WalletDto requestWallet){
        Wallet responseWallet=walletService.updateWalletById(id,requestWallet);
        return new ResponseEntity<>(responseWallet,HttpStatus.OK);
    }


}
