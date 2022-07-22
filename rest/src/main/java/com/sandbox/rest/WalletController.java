package com.sandbox.rest;

import com.sandbox.service.WalletService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class WalletController {
 WalletService walletService;
    @DeleteMapping(path = "/{id}")
    public void deleteAccount(@PathVariable("id") Long id) {
        walletService.deleteWallet(id);
    }
}
