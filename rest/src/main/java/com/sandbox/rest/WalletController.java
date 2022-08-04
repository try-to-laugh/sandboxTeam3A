package com.sandbox.rest;


import com.sandbox.api.WalletsApi;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import com.sandbox.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController implements WalletsApi {

    private final WalletService walletService;


    @Override
    @PostMapping()
    public ResponseEntity<Long> createWallet(@Valid WalletRequestDto walletRequestDto) {
        return null;
    }

    @Override
    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWalletById(@PathVariable Long walletId) {
        return null;
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<WalletResponseDto>> getAllWallets() {
        return null;
    }

    @Override
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponseDto> getWalletById(Long walletId) {
        return null;
    }

    @Override
    @PutMapping("/{walletId}")
    public ResponseEntity<WalletResponseDto> updateWalletById(@PathVariable Long walletId, @RequestBody @Valid WalletRequestDto walletRequestDto) {
        WalletResponseDto responseWallet = walletService.updateWalletById(walletId, walletRequestDto);
        return new ResponseEntity<>(responseWallet, HttpStatus.OK);


    }
}
