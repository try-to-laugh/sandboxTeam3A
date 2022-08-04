package com.sandbox.service;

import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Currency;
import com.sandbox.entity.User;
import com.sandbox.entity.Wallet;
import com.sandbox.mappers.WalletMapper;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import com.sandbox.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {WalletServiceImplTest.class, WalletMapper.class})
class WalletServiceImplTest {

    @Mock
    private WalletRepository mockWalletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;
    private WalletMapper walletMapper;


    @Test
    void updateWalletById() {

        User user = new User();
        Wallet wallet = new Wallet(1L, "testWallet", new BigDecimal(432), false, Currency.USD, user);
        WalletRequestDto changes = new WalletRequestDto();
        changes.setCurrency("USD");
        changes.setDefault(true);
        changes.setName("New name");

        Mockito.when(mockWalletRepository.findById(1L)).thenReturn(java.util.Optional.of(wallet));

        WalletResponseDto newWallet = walletService.updateWalletById(1L, changes);

        assertEquals("New name", newWallet.getName());

    }
}