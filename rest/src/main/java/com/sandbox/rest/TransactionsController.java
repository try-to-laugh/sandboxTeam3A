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
import com.sandbox.service.CategoryService;
import com.sandbox.service.TransactionService;
import com.sandbox.service.TypeService;
import com.sandbox.util.UserDetailsUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsController.class);
    private final TransactionService transactionService;

    private final MapperRest<TransactionDto, TransactionResponseDto> mapperResponseRest;
    private final MapperRest<TransactionDto, TransactionRequestDto> mapperRequestRest;
    private final TypeService typeService;
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<Long> createTransaction(@Valid TransactionRequestDto transactionRequestDto) {
        String username = UserDetailsUtil.getUsername();
        TransactionDto transactionDto = mapperRequestRest.toDto(transactionRequestDto);
        transactionDto.setCategoryId(categoryService.findByName(transactionRequestDto.getCategory().getName()).getId());
        transactionDto.setTypeId(typeService.findByName(transactionRequestDto.getTransactionType().getValue()).getId());
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
        TransactionsViewParametersDto searchParameters =
                TransactionsUtil.buildParameters(sortBy, page, size, transactionsType, filterBy, walletId);
        List<TransactionDto> transactionList = transactionService.viewTransactionsList(searchParameters);
        List<TransactionResponseDto> transactions = transactionList.stream()
                .map(mapperResponseRest::toApiDto)
                .toList();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionResponseDto> updateTransactionById(Long transactionId, @Valid TransactionRequestDto transactionRequestDto) {
        return null;
    }
}
