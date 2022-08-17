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
public class TransactionDtoResponseMapper implements MapperRest<TransactionDto, TransactionResponseDto> {

    private final CategoryService categoryService;
    private final MapperRest<CategoryDto, CategoryResponseDto> categoryMapper;
    private final WalletService walletService;
    private final TypeService typeService;


    @Override
    public TransactionDto toDto(TransactionResponseDto apiDto) {
        throw new UnsupportedOperationException("Method toDto of TransactionDtoResponseMapper not implemented");
    }

    @Override
    public TransactionResponseDto toApiDto(TransactionDto dto) {
        if (dto == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setId(dto.getId());
        responseDto.setAmount(dto.getAmount().toString());
        responseDto.setCategory(categoryMapper.toApiDto(categoryService.findById(dto.getCategoryId())));
        responseDto.setCurrency(walletService.getWalletById(dto.getWalletId(),
                UserDetailsUtil.getUsername()).getCurrency().toString());
        responseDto.setDate(dateFormat.format(dto.getDate()));
        responseDto.setPayer(dto.getPayer());
        responseDto.setNotes(dto.getNote());
        responseDto.setWalletName(walletService.getWalletById(dto.getWalletId(),
                UserDetailsUtil.getUsername()).getName());
        responseDto.setTransactionType(TransactionTypeParameter.
                fromValue(typeService.findNameById(dto.getTypeId()).get().getName()));
        return responseDto;
    }
}

