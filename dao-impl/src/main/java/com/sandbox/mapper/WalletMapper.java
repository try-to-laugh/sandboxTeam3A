package com.sandbox.mapper;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletDto toWalletDto(Wallet wallet);

    Wallet toWallet(WalletDto walletDto);
}
