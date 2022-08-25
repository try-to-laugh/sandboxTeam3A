package com.sandbox.mapper.impl;

import com.sandbox.dto.TransactionDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.model.TransactionRequestDto;
import com.sandbox.service.CategoryService;
import com.sandbox.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

@Component
@RequiredArgsConstructor
public class TransactionRequestDtoMapper implements MapperRest<TransactionDto, TransactionRequestDto> {

    private final CategoryService categoryService;
    private final TypeService typeService;

    @Override
    public TransactionDto toDto(TransactionRequestDto apiDto) {
        if (apiDto == null) {
            return null;
        }
        TransactionDto.TransactionDtoBuilder transactionDto = TransactionDto.builder();
        transactionDto.date(Date.valueOf(apiDto.getDate()));
        transactionDto.note(apiDto.getNotes());
        if (apiDto.getAmount() != null) {
            transactionDto.amount(new BigDecimal(apiDto.getAmount()));
        }

        transactionDto.payer(apiDto.getPayer());
        transactionDto.walletId(apiDto.getWalletId());
        transactionDto.categoryId(categoryService.findById(apiDto.getCategory().getId()).getId());
        transactionDto.typeId(typeService.findByName(apiDto.getTransactionType().getValue()).getId());
        return transactionDto.build();
    }


    @Override
    public TransactionRequestDto toApiDto(TransactionDto dto) {
        throw new UnsupportedOperationException("Method toApiDto of TransactionRequestDtoMapper not implemented");
    }
}
