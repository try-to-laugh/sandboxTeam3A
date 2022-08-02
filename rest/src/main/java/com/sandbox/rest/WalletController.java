package com.sandbox.rest;

import com.sandbox.dto.WalletDto;
import com.sandbox.service.UserService;
import com.sandbox.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/budget/wallet")
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createWallet(@RequestBody WalletDto walletDto) {
        walletService.createWallet(walletDto);
        return new ResponseEntity<>("New wallet created", HttpStatus.OK);
    }
}
