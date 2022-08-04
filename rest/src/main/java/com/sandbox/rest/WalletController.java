package com.sandbox.rest;


import com.sandbox.api.WalletsApi;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import com.sandbox.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Void> deleteWalletById(Long walletId) {
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
        return new ResponseEntity<WalletResponseDto>(responseWallet, HttpStatus.OK);
    }
}
