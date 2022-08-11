package com.sandbox.dto;

import com.sandbox.enums.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletDto {
    private Long id;
    private String name;
    private BigDecimal balance;
    private boolean defaultWallet;
    private Currency currency;
    private Long userId;
}
