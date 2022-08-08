package com.sandbox.mapper;

import com.sandbox.dto.WalletDto;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapperRest {

    @Mapping(source = "defaultWallet", target = "default")
    WalletResponseDto fromWalletDtoToWalletResponseDto(WalletDto walletDto);

    @Mapping(source = "default", target = "defaultWallet")
    WalletDto fromWalletRequestDtoToWalletDto(WalletRequestDto walletRequestDto);
}
