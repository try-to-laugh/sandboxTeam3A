package com.sandbox.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private Long subCategoryId;
}