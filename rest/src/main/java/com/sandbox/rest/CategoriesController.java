package com.sandbox.rest;

import com.sandbox.api.CategoriesApi;
import com.sandbox.dto.CategoryDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.model.CategoryRequestDto;
import com.sandbox.model.CategoryResponseDto;
import com.sandbox.model.TransactionTypeParameter;
import com.sandbox.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoriesController implements CategoriesApi {

    private final CategoryService categoryService;
    private final MapperRest<CategoryDto, CategoryResponseDto> categoryResponseMapperRest;
    private final MapperRest<CategoryDto, CategoryRequestDto> categoryRequestMapperRest;

    @Override
    public ResponseEntity<Long> createCategory(@Valid CategoryRequestDto categoryRequestDto) {
        CategoryDto categoryDto = categoryRequestMapperRest.toDto(categoryRequestDto);
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(Long categoryId) {
        categoryService.deleteById(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(@Valid TransactionTypeParameter categoryType) {
        List<CategoryDto> categoryDtoList = categoryService.getCategories(categoryType.getValue());
        List<CategoryResponseDto> categoryResponseDtoList = categoryDtoList.stream()
                .map(categoryResponseMapperRest::toApiDto)
                .toList();
        return new ResponseEntity<>(categoryResponseDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryResponseDto> getCategoryById(Long categoryId) {
        return null;
    }

    @Override
    public ResponseEntity<CategoryResponseDto> updateCategoryById(Long categoryId, @Valid CategoryRequestDto categoryRequestDto) {
        CategoryDto categoryDto = categoryRequestMapperRest.toDto(categoryRequestDto);
        CategoryDto resultCategoryDto = categoryService.updateCategoryById(categoryId, categoryDto);
        CategoryResponseDto categoryResponseDto = categoryResponseMapperRest.toApiDto(resultCategoryDto);
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }
}
