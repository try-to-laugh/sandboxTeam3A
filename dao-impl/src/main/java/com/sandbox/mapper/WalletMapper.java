package com.sandbox.mapper;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletRequestDto fromWalletToRequestDto(Wallet wallet);

    WalletRequestDto fromWalletDtoToWalletRequestDto(WalletDto walletDto);

    WalletResponseDto fromWalletToWalletResponseDto(Wallet wallet);

    WalletResponseDto fromWalletDtoToWalletResponseDto(WalletDto walletDto);

    WalletDto fromWalletRequestDtoToWalletDto(WalletRequestDto walletRequestDto);

    WalletDto fromWalletResponseDtoToWalletDto(WalletResponseDto walletResponseDto);

    Wallet fromWalletResponseDtoToWallet(WalletResponseDto walletResponseDto);

    WalletDto toWalletDto(Wallet wallet);

    Wallet toWallet(WalletDto walletDto);
}
