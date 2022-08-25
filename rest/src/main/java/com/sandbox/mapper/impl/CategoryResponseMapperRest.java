package com.sandbox.mapper.impl;

import com.sandbox.dto.CategoryDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.model.CategoryResponseDto;
import com.sandbox.model.TransactionTypeParameter;
import com.sandbox.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryResponseMapperRest implements MapperRest<CategoryDto, CategoryResponseDto> {

    private final TypeService typeService;

    @Override
    public CategoryDto toDto(CategoryResponseDto apiDto) {
        throw new UnsupportedOperationException("Method toDto of CategoryResponseMapperRest not implemented");
    }

    @Override
    public CategoryResponseDto toApiDto(CategoryDto dto) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(dto.getId());
        categoryResponseDto.setName(dto.getName());
        categoryResponseDto.setColor(dto.getColor());
        categoryResponseDto.setCategoryType(TransactionTypeParameter.fromValue(typeService.findNameById(dto.getTypeId()).get().getName()));
        return categoryResponseDto;
    }
}
