package com.sandbox.service;

import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.TypeDto;
import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
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
    public void deleteById(Long idTransaction, String authorizedUsername) {
        Optional<TransactionDto> transactionDto = transactionRepository.findById(idTransaction);
        if (!transactionDto.isPresent()) {
            throw new WalletNotFoundException("Impossible to delete this transaction. Transaction not found with id " + idTransaction);
        }
        Long walletId = transactionDto.get().getWalletId();
        WalletDto wallet = walletService.getWalletById(walletId, authorizedUsername);
        UserDto authorizedUsernameUser = userService.findUserByUsername(authorizedUsername);
        if (!authorizedUsernameUser.getId().equals(wallet.getUserId())) {
            throw new WalletNotFoundException("Impossible to delete this transaction. Transaction not found with id " + idTransaction);
        }
        countNewWalletBalance(transactionDto.get(), wallet);
        transactionRepository.deleteById(idTransaction);
        LOG.info("Transaction deleted");
    }

    private void countNewWalletBalance(TransactionDto transactionDto, WalletDto wallet) {
        BigDecimal newBalance = null;
        Long typeId = transactionDto.getTypeId();
        Optional<TypeDto> transactionType = typeService.findNameById(typeId);
        String typeName = transactionType.get().getName().toString();
        if (typeName.equals("INCOME")) {
            newBalance = wallet.getBalance().subtract(transactionDto.getAmount());
        } else if (typeName.equals("EXPENSE")) {
            newBalance = wallet.getBalance().add(transactionDto.getAmount());
        }
        wallet.setBalance(newBalance);
        LOG.info("Wallet balance changed");
        walletService.save(wallet);
    }
}
