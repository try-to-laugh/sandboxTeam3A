package com.sandbox.rest;

import com.sandbox.UserDetailsImpl;
import com.sandbox.api.WalletsApi;
import com.sandbox.dto.WalletDto;
import com.sandbox.mapper.WalletMapperRest;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import com.sandbox.service.WalletService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class WalletController implements WalletsApi {

    private final WalletService walletService;
    private final WalletMapperRest walletMapperRest;

    @Override
    public ResponseEntity<Long> createWallet(@Valid WalletRequestDto walletRequestDto) {
        UserDetailsImpl userDetails =(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        WalletDto walletDto = walletMapperRest.fromWalletRequestDtoToWalletDto(walletRequestDto);
        return new ResponseEntity<Long>(walletService.createWallet(walletDto, username), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity deleteWalletById(@PathVariable Long walletId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        walletService.deleteById(walletId, userName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<WalletResponseDto>> getAllWallets() {
        return null;
    }

    @Override
    public ResponseEntity<WalletResponseDto> getWalletById(@PathVariable Long walletId) {
        WalletDto walletDto = walletService.getWalletById(walletId);
        return new ResponseEntity<>(walletMapperRest.fromWalletDtoToWalletResponseDto(walletDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WalletResponseDto> updateWalletById(Long walletId, @Valid WalletRequestDto walletRequestDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        WalletDto changeWallet = walletMapperRest.fromWalletRequestDtoToWalletDto(walletRequestDto);
        WalletDto responseWallet = walletService.updateWalletById(walletId, changeWallet, userName);
        return new ResponseEntity<>(walletMapperRest.fromWalletDtoToWalletResponseDto(responseWallet), HttpStatus.OK);
    }
}
