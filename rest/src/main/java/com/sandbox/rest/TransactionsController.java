package com.sandbox.rest;

import com.sandbox.api.TransactionsApi;
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

@RequiredArgsConstructor
@RestController
public class TransactionsController implements TransactionsApi {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<Long> createTransaction(@Valid TransactionRequestDto transactionRequestDto) {
        return null;
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
        return null;
    }

    @Override
    public ResponseEntity<TransactionResponseDto> updateTransactionById(Long transactionId, @Valid TransactionRequestDto transactionRequestDto) {
        return null;
    }
}
