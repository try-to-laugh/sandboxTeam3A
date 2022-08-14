package com.sandbox.mapper;

import com.sandbox.dto.TypeDto;
import com.sandbox.entity.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    TypeDto toTypeDto(Type type);

    Type toType(TypeDto typeDto);
}
