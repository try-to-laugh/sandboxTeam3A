package com.sandbox.entity;

public enum Currency {
    USD("USD"),
    EURO("EURO");

    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
