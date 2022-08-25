package com.sandbox.service;

import com.sandbox.dto.CategoryDto;
import com.sandbox.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto findByName(String name) {
        return categoryRepository.findByName(name).get();
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("no cat"));
    }

    @Override
    public List<CategoryDto> getCategories(String categoryType) {
        return categoryRepository.getCategories(categoryType);
    }

    @Override
    public Long createCategory(CategoryDto categoryDto) {
        return categoryRepository.save(categoryDto);
    }
}
