package com.sandbox.repository;

import com.sandbox.dto.TypeDto;

import java.util.Optional;

public interface TypeRepository {

    Optional<TypeDto> findByName(String name);

    Optional<TypeDto> findById(Long id);
}
