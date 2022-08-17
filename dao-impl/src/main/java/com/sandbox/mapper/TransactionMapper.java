package com.sandbox.mapper;

import com.sandbox.dto.TransactionDto;
import com.sandbox.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto toTransactionDto(Transaction transaction);

    Transaction toTransaction(TransactionDto transactionDto);

    List<TransactionDto> toTransactionDtoList(List<Transaction> transactions);

    List<Transaction> toTransactionList(List<TransactionDto> transactionDto);
}
