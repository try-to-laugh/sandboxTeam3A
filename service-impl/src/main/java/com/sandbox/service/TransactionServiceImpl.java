package com.sandbox.service;

import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.TypeDto;
import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.enums.TypeName;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final UserService userService;
    private final TypeService typeService;

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
        countNewWalletBalance(transactionDto.get(), wallet);
        transactionRepository.deleteById(transactionId);
        LOG.info("Transaction deleted");
    }

    private void countNewWalletBalance(TransactionDto transaction, WalletDto wallet) {
        BigDecimal newBalance = null;
        Long typeId = transaction.getTypeId();
        Optional<TypeDto> transactionType = typeService.findNameById(typeId);
        TypeName typeName = transactionType.get().getName();
        if (typeName.equals(TypeName.INCOME)) {
            newBalance = wallet.getBalance().subtract(transaction.getAmount());
        } else if (typeName.equals(TypeName.EXPENSE)) {
            newBalance = wallet.getBalance().add(transaction.getAmount());
        }
        wallet.setBalance(newBalance);
        LOG.info("Wallet balance changed");
        walletService.save(wallet);
    }
}
