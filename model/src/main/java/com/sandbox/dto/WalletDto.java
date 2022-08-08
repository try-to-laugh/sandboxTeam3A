package com.sandbox.dto;

import com.sandbox.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WalletDto {
    private Long id;
    private String name;
    private BigDecimal balance;
    private boolean defaultWallet;
    private Currency currency;
    private Long userId;
}
