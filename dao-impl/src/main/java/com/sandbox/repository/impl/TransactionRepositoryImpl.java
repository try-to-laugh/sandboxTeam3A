package com.sandbox.repository.impl;

import com.sandbox.dto.TransactionDto;
import com.sandbox.entity.Transaction;
import com.sandbox.enums.TypeName;
import com.sandbox.mapper.TransactionMapper;
import com.sandbox.repository.TransactionRepository;
import com.sandbox.repository.TransactionRepositoryJpa;
import com.sandbox.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionMapper transactionMapper;
    private final TransactionRepositoryJpa transactionRepositoryJpa;
    private final TypeRepository typeRepository;


    @Override
    public List<TransactionDto> findWalletTransactions(Long walletId, String transactionType, Pageable pageable) {
        if (transactionType.equals("INCOME")) {
            return transactionMapper.toTransactionDtoList(transactionRepositoryJpa.
                    findAllByWalletIdAndTypeId(walletId,
                            typeRepository.findByName(TypeName.INCOME.getName()).get().getId(), pageable));
        }
        return transactionMapper.toTransactionDtoList(transactionRepositoryJpa.
                findAllByWalletIdAndTypeId(walletId,
                        typeRepository.findByName(TypeName.EXPENSE.getName()).get().getId(), pageable));

    }

    @Override
    public List<TransactionDto> findAllWalletTransactions(Long walletId, Pageable pageable) {
        return transactionMapper.toTransactionDtoList(transactionRepositoryJpa.
                findAllByWalletId(walletId, pageable));
    }

    @Override
    public Optional<TransactionDto> findByCategoryId(Long id) {
        Optional<Transaction> transaction = transactionRepositoryJpa.findByCategoryId(id);
        return transaction.map(transactionMapper::toTransactionDto);
    }

    @Override
    public void deleteById(Long id) {
        transactionRepositoryJpa.deleteById(id);
    }

    @Override
    public Optional<TransactionDto> findById(Long id) {
        Optional<Transaction> transaction = transactionRepositoryJpa.findById(id);
        return transaction.map(transactionMapper::toTransactionDto);
    }

    @Override
    public List<TransactionDto> findTransactions(String transactionType, Pageable pageable, Long userId) {
        if (transactionType.equals("INCOME")) {
            return transactionMapper.toTransactionDtoList(transactionRepositoryJpa.
                    findAllByTypeId(typeRepository.findByName(TypeName.INCOME.getName()).get().getId(), pageable, userId));
        }
        return transactionMapper.toTransactionDtoList(transactionRepositoryJpa.
                findAllByTypeId(typeRepository.findByName(TypeName.EXPENSE.getName()).get().getId(), pageable, userId));
    }

    @Override
    public List<TransactionDto> findAllTransactions(Pageable pageable, Long userId) {
        return transactionMapper.toTransactionDtoList(transactionRepositoryJpa.findAllTransactions(pageable, userId));
    }

    @Override
    public Long save(TransactionDto TransactionDto) {
        Transaction transaction = transactionMapper.toTransaction(TransactionDto);
        return transactionRepositoryJpa.saveAndFlush(transaction).getId();
    }
}
