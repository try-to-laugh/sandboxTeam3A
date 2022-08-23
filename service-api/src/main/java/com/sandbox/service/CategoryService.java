package com.sandbox.service;

import com.sandbox.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto findByName(String name);

    List<CategoryDto> getCategories(String categoryType);
}
