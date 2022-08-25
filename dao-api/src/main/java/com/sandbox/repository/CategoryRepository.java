package com.sandbox.repository;

import com.sandbox.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<CategoryDto> findByName(String name);

    List<CategoryDto> getCategories(String categoryType);

    Optional<CategoryDto> findById(Long id);

    void deleteById(Long id);

    Long save(CategoryDto categoryDto);
}
