package com.sandbox.rest;

import com.sandbox.service.WalletService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteWalletById(@PathVariable Long id) {
        walletService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
