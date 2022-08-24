package com.sandbox.rest;

import com.sandbox.api.WalletsApi;
import com.sandbox.dto.WalletDto;
import com.sandbox.mapper.WalletMapperRest;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import com.sandbox.service.WalletService;

import com.sandbox.util.UserDetailsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WalletsController implements WalletsApi {
    private final WalletService walletService;
    private final WalletMapperRest walletMapperRest;

    @Override
    public ResponseEntity<Long> createWallet(@Valid WalletRequestDto walletRequestDto) {
        WalletDto walletDto = walletMapperRest.fromWalletRequestDtoToWalletDto(walletRequestDto);
        return new ResponseEntity<>(walletService.createWallet(walletDto, UserDetailsUtil.getUsername()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity deleteWalletById(@PathVariable Long walletId) {
        walletService.deleteById(walletId, UserDetailsUtil.getUsername());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<WalletResponseDto>> getAllWallets() {
        List<WalletDto> walletDtoList = walletService.getWallets(UserDetailsUtil.getUsername());
        List<WalletResponseDto> walletResponseDtoList = walletDtoList.stream()
                .map(walletMapperRest::fromWalletDtoToWalletResponseDto)
                .toList();
        return ResponseEntity.ok().body(walletResponseDtoList);
    }
//new ResponseEntity<>(walletResponseDtoList, HttpStatus.OK)
    @Override
    public ResponseEntity<WalletResponseDto> getWalletById(@PathVariable Long walletId) {
        WalletDto walletDto = walletService.getWalletById(walletId, UserDetailsUtil.getUsername());
        return new ResponseEntity<>(walletMapperRest.fromWalletDtoToWalletResponseDto(walletDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WalletResponseDto> updateWalletById(Long walletId, @Valid WalletRequestDto walletRequestDto) {
        WalletDto changeWallet = walletMapperRest.fromWalletRequestDtoToWalletDto(walletRequestDto);
        WalletDto responseWallet = walletService.updateWalletById(walletId, changeWallet, UserDetailsUtil.getUsername());
        return new ResponseEntity<>(walletMapperRest.fromWalletDtoToWalletResponseDto(responseWallet), HttpStatus.OK);
    }
}
