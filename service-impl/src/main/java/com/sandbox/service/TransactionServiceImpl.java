package com.sandbox.service;

import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        if (!transactionDto.isPresent()) {
            throw new WalletNotFoundException("Impossible to delete this transaction. Transaction not found with id " + transactionId);
        }
        Long walletId = transactionDto.get().getWalletId();
        WalletDto wallet = walletService.getWalletById(walletId, authorizedUsername);
        UserDto authorizedUsernameUser = userService.findUserByUsername(authorizedUsername);
        if (!authorizedUsernameUser.getId().equals(wallet.getUserId())) {
            throw new WalletNotFoundException("Impossible to delete this transaction. Transaction not found with id " + transactionId);
        }
        walletService.updateВalance(transactionDto.get(), wallet);
        transactionRepository.deleteById(transactionId);
        LOG.info("Transaction deleted");
    }

    @Override
    public Long createTransaction(TransactionDto transactionDto, String username) {
        UserDto user = userService.findUserByUsername(username);
        Optional<WalletDto> walletOptional = user.getWallets().stream()
                .filter(walletDto -> walletDto.getId().equals(transactionDto.getWalletId())).findFirst();
        if(walletOptional.isEmpty()) {
            throw new WalletNotFoundException("Impossible to create transaction. Wallet not found");
        }
        WalletDto wallet = walletOptional.get();
        walletService.updateВalance(transactionDto, wallet);
        return transactionRepository.save(transactionDto);
    }

    @Override
    public Optional<TransactionDto> findById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }
}
