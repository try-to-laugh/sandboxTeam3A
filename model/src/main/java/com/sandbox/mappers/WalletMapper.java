package com.sandbox.mappers;

import com.sandbox.dto.WalletDto;
import com.sandbox.entities.Wallet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletDto toWalletDto(Wallet wallet);

    Wallet toWallet(WalletDto walletDto);

    List<WalletDto> toWalletDtoList(List<Wallet> wallets);
}
