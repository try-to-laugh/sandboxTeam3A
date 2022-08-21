package com.sandbox.repository.impl;

import com.sandbox.dto.TypeDto;
import com.sandbox.entity.Type;
import com.sandbox.mapper.TypeMapper;
import com.sandbox.repository.TypeRepository;
import com.sandbox.repository.TypeRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TypeRepositoryImpl implements TypeRepository {
    private final TypeRepositoryJpa typeRepositoryJpa;
    private final TypeMapper typeMapper;

    @Override
    public Optional<TypeDto> findByName(String name) {
        Optional<Type> type = typeRepositoryJpa.findByName(name);
        return type.map(typeMapper::toTypeDto);
    }

    @Override
    public Optional<TypeDto> findById(Long id) {
        Optional<Type> transactionType = typeRepositoryJpa.findById(id);
        return transactionType.map(typeMapper::toTypeDto);
    }
}
