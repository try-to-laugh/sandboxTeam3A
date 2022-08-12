package com.sandbox.mapper;

import com.sandbox.dto.CategoryDto;
import com.sandbox.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);
}
