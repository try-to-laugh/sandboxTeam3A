package com.sandbox.rest;

import com.sandbox.api.TransactionsApi;
import com.sandbox.dto.TransactionsViewParametersDto;
import com.sandbox.mapper.MapperRest;
import com.sandbox.dto.TransactionDto;
import com.sandbox.model.FilterParameter;
import com.sandbox.model.SortParameter;
import com.sandbox.model.TransactionRequestDto;
import com.sandbox.model.TransactionResponseDto;
import com.sandbox.model.TransactionTypeParameter;
import com.sandbox.service.TransactionService;
import com.sandbox.util.UserDetailsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import com.sandbox.util.TransactionsUtil;

@RequiredArgsConstructor
@RestController
public class TransactionsController implements TransactionsApi {

    private final TransactionService transactionService;

    private final MapperRest<TransactionDto, TransactionResponseDto> mapperResponseRest;
    private final MapperRest<TransactionDto, TransactionRequestDto> mapperRequestRest;

    @Override
    public ResponseEntity<Long> createTransaction(@Valid TransactionRequestDto transactionRequestDto) {
        String username = UserDetailsUtil.getUsername();
        TransactionDto transactionDto = mapperRequestRest.toDto(transactionRequestDto);
        return new ResponseEntity<>(transactionService.createTransaction(transactionDto, username), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteTtransactionById(Long transactionId) {
        transactionService.deleteById(transactionId, UserDetailsUtil.getUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<TransactionResponseDto> getTransactionById(Long transactionId) {
        return null;
    }

    @Override
    public ResponseEntity<List<TransactionResponseDto>> getTransactions(@NotNull @Valid SortParameter sortBy, @NotNull @Valid Long page, @NotNull @Valid Long size, @Valid TransactionTypeParameter transactionsType, @Valid List<FilterParameter> filterBy, @Valid Long walletId) {
        String username = UserDetailsUtil.getUsername();
        TransactionsViewParametersDto searchParameters =
                TransactionsUtil.buildParameters(sortBy, page, size, transactionsType, filterBy, walletId);
        List<TransactionDto> transactionList = transactionService.viewTransactionsList(searchParameters, username);
        List<TransactionResponseDto> transactions = transactionList.stream()
                .map(mapperResponseRest::toApiDto)
                .toList();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionResponseDto> updateTransactionById(Long transactionId, @Valid TransactionRequestDto transactionRequestDto) {
        TransactionDto transactionDto = mapperRequestRest.toDto(transactionRequestDto);
        String username = UserDetailsUtil.getUsername();
        TransactionDto resultTransaction = transactionService.updateTransactionById(transactionId, transactionDto, username);
        TransactionResponseDto transactionResponseDto = mapperResponseRest.toApiDto(resultTransaction);
        return new ResponseEntity<>(transactionResponseDto, HttpStatus.OK);
    }
}
