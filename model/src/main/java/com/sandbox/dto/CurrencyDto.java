package com.sandbox.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurrencyDto {
    USD("USD"),
    EURO("EURO");

    private final String currency;
}
