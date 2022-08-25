package com.sandbox.repository.impl;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.exception.BudgetRuntimeException;
import com.sandbox.mapper.WalletMapper;
import com.sandbox.repository.WalletRepository;
import com.sandbox.repository.WalletRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepository {
    private final WalletRepositoryJpa walletRepositoryJpa;
    private final WalletMapper walletMapper;
    @PersistenceUnit
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnitName");

    @Override
    public Optional<WalletDto> findById(Long id) {
        Optional<Wallet> wallet = walletRepositoryJpa.findById(id);
        if (wallet.isPresent() & wallet.get().isArchiveWallet()) {
                return Optional.empty();
        }
        return wallet.map(walletMapper::toWalletDto);
    }

    @Override
    public Optional<WalletDto> findByStatus(Boolean walletStatus, Long walletOwnerId) {
        Optional<Wallet> wallet = walletRepositoryJpa.findByDefault(walletStatus, walletOwnerId);
        return wallet.map(walletMapper::toWalletDto);
    }

    @Override
    public Long save(WalletDto walletDto) {
        try {
            Wallet savedWallet = walletMapper.toWallet(walletDto);
            return walletRepositoryJpa.saveAndFlush(savedWallet).getId();
        } catch (DataIntegrityViolationException ex) {
            throw new BudgetRuntimeException("A wallet with such a name and currency already exists. "
                    + "Please, change the name", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        walletRepositoryJpa.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<WalletDto> findWalletWithMaxBalance(Long userId, Long walletIdIgnore) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Wallet walletWithMaxBalance = (Wallet) entityManager
                .createQuery("SELECT wallet FROM Wallet wallet WHERE wallet.userId = ?1 AND wallet.id <> ?2 AND " +
                        "wallet.balance = (SELECT MAX(balance) FROM Wallet WHERE userId = ?1 AND id <> ?2)")
                .setParameter(1, userId)
                .setParameter(2, walletIdIgnore)
                .getSingleResult();
        entityManager.getTransaction().commit();
        WalletDto walletDto = walletMapper.toWalletDto(walletWithMaxBalance);
        return Optional.of(walletDto);
    }

    @Override
    public List<WalletDto> findAll(Long userId) {
        return walletRepositoryJpa.findAll(userId).stream().map(walletMapper::toWalletDto).toList();
    }

    @Override
    public Long countTransactionByWalletId(Long walletId) {
        return walletRepositoryJpa.countTransactionByWalletId(walletId);
    }

}
