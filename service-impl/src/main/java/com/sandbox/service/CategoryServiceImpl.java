package com.sandbox.service;

import com.sandbox.dto.CategoryDto;
import com.sandbox.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto findByName(String name) {
        return categoryRepository.findByName(name).get();
    }
}
