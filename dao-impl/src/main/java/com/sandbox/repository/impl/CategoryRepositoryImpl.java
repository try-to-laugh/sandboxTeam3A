package com.sandbox.repository.impl;

import com.sandbox.dto.CategoryDto;
import com.sandbox.entity.Category;
import com.sandbox.mapper.CategoryMapper;
import com.sandbox.repository.CategoryRepository;
import com.sandbox.repository.CategoryRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryRepositoryJpa categoryRepositoryJpa;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<CategoryDto> findByName(String name) {
        Optional<Category> category = categoryRepositoryJpa.findByName(name);
        return category.map(categoryMapper::toCategoryDto);
    }
}
