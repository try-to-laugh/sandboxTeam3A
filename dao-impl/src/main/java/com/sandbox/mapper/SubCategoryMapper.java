package com.sandbox.mapper;

import com.sandbox.dto.SubCategoryDto;
import com.sandbox.entity.SubCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {

    SubCategoryDto toSubCategoryDto(SubCategory subCategory);

    SubCategory toSubCategory(SubCategoryDto subCategoryDto);
}
