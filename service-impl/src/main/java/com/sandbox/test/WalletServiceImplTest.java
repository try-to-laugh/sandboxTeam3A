package com.sandbox.test;

import com.sandbox.entity.Currency;
import com.sandbox.entity.User;
import com.sandbox.entity.Wallet;
import com.sandbox.exception.ResourceNotFoundException;
import com.sandbox.repository.WalletRepository;
import com.sandbox.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@SpringBootTest(classes = WalletServiceImplTest.class)
class WalletServiceImplTest {
    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository mockWalletRepository;


    @Test
    void deleteById() {
        Wallet wallet = new Wallet(1L, "w1", new BigDecimal(44), true, Currency.USD, new User());
        Mockito.doAnswer(delete -> {
                    String deleted ="deleted";
                    assertEquals("deleted", deleted);
                    return null;
                }
        ).when(mockWalletRepository).deleteById(1L);
        Mockito.when(mockWalletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        walletService.deleteById(1L);
        verify(mockWalletRepository).deleteById(1L);
    }

    @Test
    public void walletNotExistForDeletingTest() {
        Mockito.doThrow(ResourceNotFoundException.class)
                .when(mockWalletRepository).deleteById(1L);
    }
}