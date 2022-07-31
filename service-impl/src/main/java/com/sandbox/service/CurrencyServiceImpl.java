package com.sandbox.service;

import com.sandbox.entities.Currency;
import com.sandbox.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public Optional<Currency> findById(Long id) {
        return currencyRepository.findById(id);
    }
}
