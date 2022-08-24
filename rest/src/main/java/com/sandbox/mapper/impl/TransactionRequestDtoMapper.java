package com.sandbox.mapper.impl;

import com.sandbox.dto.TransactionDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.model.TransactionRequestDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

@Component
public class TransactionRequestDtoMapper implements MapperRest<TransactionDto, TransactionRequestDto> {

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
        return transactionDto.build();
    }


    @Override
    public TransactionRequestDto toApiDto(TransactionDto dto) {
        throw new UnsupportedOperationException("Method toApiDto of TransactionRequestDtoMapper not implemented");
    }
}
