package com.sandbox.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    USD("USD"),
    EUR("EUR"),
    GEL("GEL"),
    PLN("PLN");

    private final String currency;
}
