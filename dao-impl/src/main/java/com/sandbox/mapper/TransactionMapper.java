package com.sandbox.mapper;

import com.sandbox.dto.TransactionDto;
import com.sandbox.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto toTransactionDto(Transaction transaction);

    Transaction toTransaction(TransactionDto transactionDto);
}
