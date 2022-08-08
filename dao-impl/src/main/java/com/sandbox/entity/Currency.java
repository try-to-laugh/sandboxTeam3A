package com.sandbox.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    USD("USD"),
    EURO("EURO");

    private final String currency;
}
