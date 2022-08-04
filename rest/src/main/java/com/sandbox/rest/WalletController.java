package com.sandbox.rest;

import com.sandbox.dto.WalletDto;
import com.sandbox.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/all")
    public ResponseEntity<List<WalletDto>> getAllWallets(){
        return ResponseEntity.ok(walletService.allWallet());
    }
}
