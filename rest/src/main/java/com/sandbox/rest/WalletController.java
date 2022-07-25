package com.sandbox.rest;

import com.sandbox.service.WalletService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    private WalletService walletService;

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity deleteAccount(@PathVariable("id") Long id) {
        walletService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
