package com.sandbox.service;

import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.TransactionsViewParametersDto;
import com.sandbox.dto.TypeDto;
import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.repository.TransactionRepository;
import com.sandbox.enums.TypeName;
import com.sandbox.exception.WalletNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final UserService userService;

    @Override
    public void deleteById(Long transactionId, String authorizedUsername) {
        Optional<TransactionDto> transactionDto = transactionRepository.findById(transactionId);
        if (transactionDto.isEmpty()) {
            throw new WalletNotFoundException("Impossible to delete this transaction. Transaction not found with id " + transactionId);
        }
        Long walletId = transactionDto.get().getWalletId();
        WalletDto wallet = walletService.getWalletById(walletId, authorizedUsername);
        UserDto authorizedUsernameUser = userService.findUserByUsername(authorizedUsername);
        if (!authorizedUsernameUser.getId().equals(wallet.getUserId())) {
            throw new WalletNotFoundException("Impossible to delete this transaction. Transaction not found with id " + transactionId);
        }
        walletService.updateBalance(transactionDto.get(), wallet, true);
        transactionRepository.deleteById(transactionId);
        LOG.info("Transaction deleted");
    }

    @Override
    public Long createTransaction(TransactionDto transactionDto, String username) {
        UserDto user = userService.findUserByUsername(username);
        Optional<WalletDto> walletOptional = user.getWallets().stream()
                .filter(walletDto -> walletDto.getId().equals(transactionDto.getWalletId())).findFirst();
        if (walletOptional.isEmpty()) {
            throw new WalletNotFoundException("Impossible to create transaction. Wallet not found");
        }
        WalletDto wallet = walletOptional.get();
        walletService.updateBalance(transactionDto, wallet, false);
        Long id = transactionRepository.save(transactionDto);
        LOG.info("Transaction created");
        return id;

    }

    @Override
    public TransactionDto updateTransactionById(Long transactionId, TransactionDto transactionDto, String username) {
        UserDto user = userService.findUserByUsername(username);
        Optional<WalletDto> walletOptional = user.getWallets().stream()
                .filter(walletDto -> walletDto.getId().equals(transactionDto.getWalletId())).findFirst();
        if (walletOptional.isEmpty()) {
            throw new WalletNotFoundException("Impossible to update transaction. Wallet not found");
        }
        WalletDto wallet = walletOptional.get();
        walletService.updateBalance(transactionDto, wallet, false);
        deleteById(transactionId, username);
        Long newId = transactionRepository.save(transactionDto);
        LOG.info("Transaction created");
        transactionDto.setId(newId);
        return transactionDto;
    }

    @Override
    public List<TransactionDto> viewTransactionsList(TransactionsViewParametersDto searchParameters, String username) {
        UserDto user = userService.findUserByUsername(username);
        Pageable page =
                switch (searchParameters.getSortParameter()) {
                    case "dateasc" -> PageRequest.of(Math.toIntExact(searchParameters.getPage()),
                            Math.toIntExact(searchParameters.getSize()),
                            Sort.by("date").ascending());
                    case "amountdesc" -> PageRequest.of(Math.toIntExact(searchParameters.getPage()),
                            Math.toIntExact(searchParameters.getSize()),
                            Sort.by("amount").descending());
                    case "amountasc" -> PageRequest.of(Math.toIntExact(searchParameters.getPage()),
                            Math.toIntExact(searchParameters.getSize()),
                            Sort.by("amount").ascending());
                    default -> PageRequest.of(Math.toIntExact(searchParameters.getPage()),
                            Math.toIntExact(searchParameters.getSize()),
                            Sort.by("date").descending());
                };

        if (searchParameters.getWalletId() != null) {
            if (searchParameters.getTransactionType() != null) {
                return transactionRepository.findWalletTransactions(
                        searchParameters.getWalletId(),
                        searchParameters.getTransactionType(),
                        page);
            } else {
                return transactionRepository
                        .findAllWalletTransactions(searchParameters.getWalletId(), page);
            }
        }

        if (searchParameters.getTransactionType() != null) {
            return transactionRepository.findTransactions(searchParameters.getTransactionType(), page, user.getId());
        } else {
            return transactionRepository.findAllTransactions(page, user.getId());
        }
    }

    public Optional<TransactionDto> findById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }
}
