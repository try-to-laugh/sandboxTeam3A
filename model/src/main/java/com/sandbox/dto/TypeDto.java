package com.sandbox.dto;

import com.sandbox.enums.TypeName;
import lombok.Data;

@Data
public class TypeDto {
    private Long id;
    private TypeName name;
}
