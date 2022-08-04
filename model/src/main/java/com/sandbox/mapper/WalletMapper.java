package com.sandbox.mapper;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletRequestDto fromWalletToRequestDto(Wallet wallet);

    WalletRequestDto fromWalletResponseDtoToWalletRequestDto(WalletResponseDto walletResponseDto);

    WalletResponseDto fromWalletToWalletResponseDto(Wallet wallet);

    WalletResponseDto fromWalletRequestDtoToWalletResponseDto(WalletRequestDto walletRequestDtoDto);

    Wallet fromWalletRequestDtoToWallet(WalletRequestDto walletRequestDto);

    Wallet fromWalletResponseDtoToWallet(WalletResponseDto walletResponseDto);


    List<WalletDto> toWalletDtoList(List<Wallet> wallets);
}
