package com.sandbox.service;

import com.sandbox.entities.Currency;

import java.util.Optional;

public interface CurrencyService {
    Optional<Currency> findById(Long id);
}
