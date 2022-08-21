package com.sandbox.service;

import com.sandbox.dto.TypeDto;

import java.util.Optional;

public interface TypeService {

    Optional<TypeDto> findNameById(Long id);

    TypeDto findByName(String name);
}
