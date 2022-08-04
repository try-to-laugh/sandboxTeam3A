package com.sandbox.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    USD("USD"),
    EUR("EUR"),
    UAH("UAH"),
    PLZ("PLZ");

    private final String currency;
}
