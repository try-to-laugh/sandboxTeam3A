package com.sandbox.service;


import com.sandbox.dto.TransactionDto;
import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.dto.TypeDto;
import com.sandbox.dto.CategoryDto;
import com.sandbox.enums.Currency;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = TransactionServiceImplTest.class)
class TransactionServiceImplTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository mockTransactionRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private WalletService mockWalletService;
    @Mock
    private TypeService mockTypeService;

    private final TransactionDto transaction = TransactionDto.builder()
            .id(1L)
            .amount(new BigDecimal(100))
            .date(new Date(01 - 11 - 2020))
            .payer("Lilly")
            .note("")
            .categoryId(1L)
            .typeId(1L)
            .walletId(1L)
            .build();

    private final WalletDto wallet = WalletDto.builder()
            .id(1L)
            .name("wallet1")
            .balance(new BigDecimal(2000))
            .defaultWallet(false)
            .currency(Currency.EUR)
            .userId(1L)
            .build();

    private final UserDto walletOwner = UserDto.builder()
            .id(1L)
            .name("Aleks")
            .surname("Palos")
            .username("Petir")
            .password("qwerty123")
            .roles(new HashSet<>())
            .wallets(new HashSet<>())
            .build();

    private final TypeDto type = TypeDto.builder()
            .id(1L)
            .name("INCOME")
            .build();

    private final CategoryDto category = CategoryDto.builder()
            .id(1L)
            .name("Food")
            .color("blue")
            .typeId(1L)
            .build();

    @Test
    public void deleteById() {
        Mockito.doAnswer(delete -> {
                    String deleted = "deleted";
                    assertEquals("deleted", deleted);
                    return null;
                }
        ).when(mockTransactionRepository).deleteById(1L);
        Mockito.when(mockTransactionRepository.findById(wallet.getId())).thenReturn(Optional.of(transaction));
        Mockito.when(mockWalletService.getWalletById(wallet.getId(), "Petir")).thenReturn(wallet);
        Mockito.when(mockUserService.findUserByUsername("Petir")).thenReturn(walletOwner);
        Mockito.when(mockTypeService.findNameById(type.getId())).thenReturn(Optional.of(type));
        transactionService.deleteById(1L, walletOwner.getUsername());
        verify(mockTransactionRepository).deleteById(1L);
    }

    @Test
    public void transactionNotFoundTest() {
        Set<TransactionDto> transactions = new HashSet<>();
        transactions.add(transaction);
        Mockito.when(mockTransactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        assertThatThrownBy(() -> transactionService.deleteById(2L, "johnbullon"))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Impossible to delete this transaction. Transaction not found with id " + 2L);
    }
}
