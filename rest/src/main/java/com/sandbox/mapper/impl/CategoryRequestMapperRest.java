package com.sandbox.mapper.impl;

import com.sandbox.dto.CategoryDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.model.CategoryRequestDto;
import com.sandbox.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryRequestMapperRest implements MapperRest<CategoryDto, CategoryRequestDto> {

    private final TypeService typeService;

    @Override
    public CategoryDto toDto(CategoryRequestDto apiDto) {
        return CategoryDto.builder()
                .name(apiDto.getName())
                .color(apiDto.getColor())
                .typeId(typeService.findByName(apiDto.getCategoryType().getValue()).getId())
                .build();
    }

    @Override
    public CategoryRequestDto toApiDto(CategoryDto dto) {
        throw new UnsupportedOperationException("Method toApiDto of CategoryRequestMapperRest not implemented");
    }
}
