package com.sandbox.dto;


import com.sandbox.entities.Wallet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletDto {
    private Long wallet_id;
    private String walletName;

    public static WalletDto getData(Wallet walletEntity) {
        return WalletDto.builder()
                .wallet_id(walletEntity.getWallet_id())
                .walletName(walletEntity.getWalletName())
                .build();
    }
}
