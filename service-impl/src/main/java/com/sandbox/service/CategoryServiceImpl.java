package com.sandbox.service;

import com.sandbox.dto.CategoryDto;
import com.sandbox.exception.BudgetRuntimeException;
import com.sandbox.exception.CategoryNotFoundException;
import com.sandbox.repository.CategoryRepository;
import com.sandbox.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final TransactionRepository transactionRepository;

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
    public void deleteById(Long id) {
        if (transactionRepository.findByCategoryId(id).isPresent()) {
            throw new BudgetRuntimeException("Transactions with this category already exist," +
                    " please delete transactions first");
        }
        categoryRepository.deleteById(id);
    }

    public Long createCategory(CategoryDto categoryDto) {
        return categoryRepository.save(categoryDto);
    }

    @Override
    public CategoryDto updateCategoryById(Long categoryId, CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException("Category with  id = " + categoryId + " not found'"));
        updatedCategory.setName(categoryDto.getName());
        updatedCategory.setColor(categoryDto.getColor());
        updatedCategory.setTypeId(categoryDto.getTypeId());
        categoryRepository.save(updatedCategory);
        return updatedCategory;
    }
}
