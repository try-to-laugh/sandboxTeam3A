package com.sandbox.mapper.impl;

import com.sandbox.dto.TransactionDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.mapper.TransactionMapperRest;
import com.sandbox.model.TransactionRequestDto;
import com.sandbox.service.CategoryService;
import com.sandbox.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransactionRequestMapperRest implements MapperRest<TransactionDto, TransactionRequestDto> {

    private final CategoryService categoryService;
    private final TypeService typeService;

    @Override
    public TransactionDto toDto(TransactionRequestDto transactionRequestDto) {
        if (transactionRequestDto == null) {
            return null;
        } else {
            TransactionDto.TransactionDtoBuilder transactionDto = TransactionDto.builder();
            transactionDto.date(TransactionMapperRest.stringDateToSQLDate(transactionRequestDto.getDate()));
            transactionDto.note(transactionRequestDto.getNotes());
            if (transactionRequestDto.getAmount() != null) {
                transactionDto.amount(new BigDecimal(transactionRequestDto.getAmount()));
            }
            transactionDto.payer(transactionRequestDto.getPayer());
            transactionDto.walletId(transactionRequestDto.getWalletId());
            transactionDto.categoryId(categoryService.findByName(transactionRequestDto.getCategory().getName()).getId());
            transactionDto.typeId(typeService.findByName(transactionRequestDto.getTransactionType().getValue()).getId());
            return transactionDto.build();
        }
    }

    @Override
    public TransactionRequestDto toApiDto(TransactionDto dto) {
        throw new UnsupportedOperationException("Method toApiDto of TransactionRequestMapperRest not implemented");
    }
}
