package com.sandbox.dto;

import com.sandbox.enums.Currency;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class WalletDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    @EqualsAndHashCode.Exclude
    private BigDecimal balance;
    @EqualsAndHashCode.Exclude
    private boolean defaultWallet;
    private Currency currency;
    private Long userId;
    private boolean archiveWallet;
}
