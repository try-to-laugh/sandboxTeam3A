package com.sandbox.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    USD("dollar"),
    EUR("euro"),
    PLN("złoty"),
    GEL("lari");

    private final String currency;
}
