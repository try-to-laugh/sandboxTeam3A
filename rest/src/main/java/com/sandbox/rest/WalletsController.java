package com.sandbox.rest;

import com.sandbox.api.WalletsApi;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import com.sandbox.service.WalletService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletsController implements WalletsApi {
    private final WalletService walletService;

    @Override
    public ResponseEntity<Long> createWallet(@Valid WalletRequestDto walletRequestDto) {
        return null;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteWalletById(@PathVariable Long id) {
        walletService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<WalletResponseDto>> getAllWallets() {
        return null;
    }

    @Override
    public ResponseEntity<WalletResponseDto> getWalletById(Long walletId) {
        return null;
    }

    @Override
    public ResponseEntity<WalletResponseDto> updateWalletById(Long walletId, @Valid WalletRequestDto walletRequestDto) {
        WalletResponseDto responseWallet = walletService.updateWalletById(walletId, walletRequestDto);
        return new ResponseEntity<>(responseWallet, HttpStatus.OK);
    }
}
