package com.sandbox.service;

import com.sandbox.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto findByName(String name);

    CategoryDto findById(Long id);

    List<CategoryDto> getCategories(String categoryType);
}
