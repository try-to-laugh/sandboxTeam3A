import com.sandbox.dto.CurrencyDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.enums.Currency;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.WalletRepository;
import com.sandbox.repository.WalletRepositoryJpa;
import com.sandbox.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@SpringBootTest(classes = WalletServiceImplTest.class)
class WalletServiceImplTest {
    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepositoryJpa mockWalletRepository;

    @Mock
    private WalletRepository walletRepository;

    @Test
    void deleteById() {
        Wallet wallet = new Wallet(1L, "w1", new BigDecimal(44), true, Currency.USD, 1L);
        Mockito.doAnswer(delete -> {
                    String deleted = "deleted";
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
        Wallet wallet = new Wallet(1L, "w1", new BigDecimal(44), true, Currency.USD, 1L);
        assertThatThrownBy(() -> walletService.deleteById(1L))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Impossible to delete this wallet. Wallet not found with id " + wallet.getId());
    }

    @Test
    public void getWalletByIdTest() {
        WalletDto wallet = new WalletDto();
        wallet.setId(1L);
        wallet.setName("my wallet");
        wallet.setBalance(new BigDecimal(44));
        wallet.setCurrency(CurrencyDto.USD);
        wallet.setUserId(1L);
        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        WalletDto actualWallet = walletService.getWalletById(1L);
        assertEquals(wallet, actualWallet);
    }

    @Test
    void walletNotFoundGetByIdTest() {
        assertThatThrownBy(() -> walletService.getWalletById(20L))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("wallet with  id = 20 not found");
    }
}