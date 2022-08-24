package com.sandbox.mapper.impl;

import com.sandbox.dto.CategoryDto;
import com.sandbox.dto.TransactionDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.model.CategoryResponseDto;
import com.sandbox.model.TransactionResponseDto;
import com.sandbox.model.TransactionTypeParameter;
import com.sandbox.service.CategoryService;
import com.sandbox.service.TypeService;
import com.sandbox.service.WalletService;
import com.sandbox.util.UserDetailsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
@RequiredArgsConstructor
public class TransactionResponseMapperRest implements MapperRest<TransactionDto, TransactionResponseDto> {

    private final TypeService typeService;
    private final WalletService walletService;
    private final CategoryService categoryService;
    private final MapperRest<CategoryDto, CategoryResponseDto> categoryMapper;

    @Override
    public TransactionDto toDto(TransactionResponseDto apiDto) {
        throw new UnsupportedOperationException("Method toDto of TransactionResponseMapperRest not implemented");
    }

    @Override
    public TransactionResponseDto toApiDto(TransactionDto transactionDto) {
        if (transactionDto == null) {
            return null;
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            TransactionResponseDto responseDto = new TransactionResponseDto();
            responseDto.setId(transactionDto.getId());
            responseDto.setAmount(transactionDto.getAmount().toString());
            responseDto.setCategory(categoryMapper.toApiDto(categoryService.findById(transactionDto.getCategoryId())));
            responseDto.setCurrency(walletService.getWalletById(transactionDto.getWalletId(),
                    UserDetailsUtil.getUsername()).getCurrency().toString());
            responseDto.setDate(dateFormat.format(transactionDto.getDate()));
            responseDto.setPayer(transactionDto.getPayer());
            responseDto.setNotes(transactionDto.getNote());
            responseDto.setWalletName(walletService.getWalletById(transactionDto.getWalletId(),
                    UserDetailsUtil.getUsername()).getName());
            responseDto.setTransactionType(TransactionTypeParameter.
                    fromValue(typeService.findNameById(transactionDto.getTypeId()).get().getName()));
            return responseDto;
        }
    }
}

