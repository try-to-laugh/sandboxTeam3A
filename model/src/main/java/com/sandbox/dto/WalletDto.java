package com.sandbox.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    private Long id;
    private String name;
    private BigDecimal balance;
    private boolean defaultWallet;
    private CurrencyDto currency;
    private Long userId;
}
