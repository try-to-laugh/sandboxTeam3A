package com.sandbox.repository;

import com.sandbox.dto.CategoryDto;

import java.util.Optional;

public interface CategoryRepository {

    Optional<CategoryDto> findByName(String name);
}
