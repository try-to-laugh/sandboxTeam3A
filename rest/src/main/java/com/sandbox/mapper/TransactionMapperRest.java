package com.sandbox.mapper;

import com.sandbox.dto.TransactionDto;
import com.sandbox.model.TransactionRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Date;

@Mapper(componentModel = "spring")
public interface TransactionMapperRest {

    @Named("stringDateToSQLDate")
    static Date stringDateToSQLDate(String date) {
        return Date.valueOf(date);
    }

    @Mapping(target = "date", source = "transactionRequestDto.date", qualifiedByName = "stringDateToSQLDate")
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "typeId", ignore = true)
    @Mapping(target = "note", source = "notes")
    TransactionDto fromTransactionRequestDtoToTransactionDto(TransactionRequestDto transactionRequestDto);
}