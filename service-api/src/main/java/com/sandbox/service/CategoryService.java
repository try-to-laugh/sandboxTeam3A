package com.sandbox.service;

import com.sandbox.dto.CategoryDto;

public interface CategoryService {

    CategoryDto findByName(String name);
}
