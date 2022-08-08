package com.sandbox.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurrencyDto {
    USD("USD"),
    EUR("EUR"),
    UAH("UAH"),
    PLZ("PLZ;");

    private final String currency;
}
