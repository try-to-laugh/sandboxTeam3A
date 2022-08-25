package com.sandbox.service;

import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.TypeDto;
import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.enums.TypeName;
import com.sandbox.exception.BudgetRuntimeException;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);
    private final WalletRepository walletRepository;
    private final UserService userService;
    private final TypeService typeService;

    @Override
    public Long createWallet(WalletDto walletDto, String username) {
        UserDto userDto = userService.findUserByUsername(username);
        walletDto.setUserId(userDto.getId());
        walletDto.setBalance(BigDecimal.valueOf(0));
        Set<WalletDto> userWallets = userDto.getWallets();
        if (userWallets.isEmpty()) {
            walletDto.setDefaultWallet(true);
        } else if (walletDto.isDefaultWallet()) {
            Optional<WalletDto> walletDefault = userWallets.stream().filter(WalletDto::isDefaultWallet).findFirst();
            if (walletDefault.isPresent()) {
                WalletDto makeFalseWallet = walletDefault.get();
                makeFalseWallet.setDefaultWallet(false);
                walletRepository.save(makeFalseWallet);
            }
        }
        if (userWallets.contains(walletDto)) {
            throw new BudgetRuntimeException("Such wallet already exists, please choose another currency or change the name of wallet");
        }
        return walletRepository.save(walletDto);
    }

    @Override
    public void deleteById(Long id, String username) {
        UserDto walletOwner = userService.findUserByUsername(username);
        Long walletOwnerId = walletOwner.getId();
        WalletDto walletWhichWantToDelete = walletRepository.findById(id).orElseThrow(() ->
                new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id));
        if (!walletWhichWantToDelete.getUserId().equals(walletOwnerId)) {
            throw new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id);
        }
        if (walletWhichWantToDelete.isDefaultWallet()) {
            changeDefaultWallet(walletOwner, id);
        }
        if (walletRepository.countTransactionByWalletId(walletWhichWantToDelete.getId()) > 0) {
            walletWhichWantToDelete.setArchiveWallet(true);
            walletRepository.save(walletWhichWantToDelete);
            LOG.info("The wallet was archived");
        } else {
            walletRepository.deleteById(id);
            LOG.info("The wallet was successfully deleted");
        }
    }

    @Override
    public List<WalletDto> getWallets(String username) {
        UserDto userDto = userService.findUserByUsername(username);
        Long walletOwnerId = userDto.getId();
        return walletRepository.findAll(walletOwnerId);
    }

    @Override
    public Long save(WalletDto walletDto) {
        return walletRepository.save(walletDto);
    }

    private void changeDefaultWallet(UserDto walletOwner, Long idWalletWhichDeleting) {
        if (walletOwner.getWallets().size() > 1) {
            Optional<WalletDto> walletWithMaxBalance = walletRepository.findWalletWithMaxBalance(walletOwner.getId(), idWalletWhichDeleting);
            if (walletWithMaxBalance.isPresent()) {
                walletWithMaxBalance.get().setDefaultWallet(true);
                walletRepository.save(walletWithMaxBalance.get());
            }
            LOG.info("Default wallet successfully changed");
        }
    }

    public WalletDto updateWalletById(Long walletId, WalletDto requestWallet, String username) {
        UserDto userDto = userService.findUserByUsername(username);
        Long walletOwnerId = userDto.getId();
        WalletDto updatedWallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with  id = " + walletId + " not found'"));
        if (!updatedWallet.getUserId().equals(walletOwnerId)) {
            throw new WalletNotFoundException("Wallet with  id = " + walletId + " not found");
        }
        Optional<WalletDto> defaultWallet =
                walletRepository.findByStatus(true, walletOwnerId);
        if (defaultWallet.isPresent()) {
            WalletDto makeFalseWallet = defaultWallet.get();
            if (requestWallet.isDefaultWallet()) {
                if (!makeFalseWallet.getId().equals(walletId)) {
                    makeFalseWallet.setDefaultWallet(false);
                    walletRepository.save(makeFalseWallet);
                }
            } else if (makeFalseWallet.getId().equals(walletId)) {
                throw new BudgetRuntimeException("at least one wallet should be default");
            }
        }

        updatedWallet.setName(requestWallet.getName());
        updatedWallet.setCurrency(requestWallet.getCurrency());
        updatedWallet.setDefaultWallet(requestWallet.isDefaultWallet());

        walletRepository.save(updatedWallet);

        return updatedWallet;
    }

    public WalletDto getWalletById(Long walletId, String username) {
        UserDto userDto = userService.findUserByUsername(username);
        Long walletOwnerId = userDto.getId();
        WalletDto walletDto = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("This wallet doesn't exist"));
        if (!walletDto.getUserId().equals(walletOwnerId)) {
            throw new WalletNotFoundException("Wallet not found");
        }
        return walletDto;
    }

    public void updateBalance(TransactionDto transaction, WalletDto wallet, boolean rollback) {
        BigDecimal newBalance = null;
        Long typeId = transaction.getTypeId();
        Optional<TypeDto> transactionType = typeService.findNameById(typeId);
        TypeName typeName = TypeName.valueOf(transactionType.get().getName());
        if(rollback) {
            if (typeName.equals(TypeName.INCOME)) {
                newBalance = wallet.getBalance().subtract(transaction.getAmount());
            } else if (typeName.equals(TypeName.EXPENSE)) {
                newBalance = wallet.getBalance().add(transaction.getAmount());
            }
        } else {
            if (typeName.equals(TypeName.INCOME)) {
                newBalance = wallet.getBalance().add(transaction.getAmount());
            } else if (typeName.equals(TypeName.EXPENSE)) {
                newBalance = wallet.getBalance().subtract(transaction.getAmount());
            }
        }
        wallet.setBalance(newBalance);
        LOG.info("Wallet balance changed");
        save(wallet);
    }

    @Override
    public long countTransactionByWalletId(Long walletId) {
        return walletRepository.countTransactionByWalletId(walletId);
    }
}
