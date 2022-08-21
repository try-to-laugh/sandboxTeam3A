package com.sandbox.service;

import com.sandbox.dto.TypeDto;
import com.sandbox.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;

    @Override
    public Optional<TypeDto> findNameById(Long id) {
        return typeRepository.findById(id);
    }

    @Override
    public TypeDto findByName(String name) {
        return typeRepository.findByName(name).get();
    }
}
