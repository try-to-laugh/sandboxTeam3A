import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.enums.Currency;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.UserRepository;
import com.sandbox.repository.WalletRepository;
import com.sandbox.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;


@SpringBootTest(classes = WalletServiceImplTest.class)
class WalletServiceImplTest {
    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository mockWalletRepository;
    @Mock
    private UserRepository mockUserRepository;


    @Test
    void deleteById() {
        WalletDto wallet = WalletDto.builder()
                .id(1L)
                .name("wallet1")
                .balance(new BigDecimal(4232))
                .defaultWallet(false)
                .currency(Currency.PLZ)
                .userId(1L)
                .build();
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
        WalletDto wallet = WalletDto.builder()
                .id(1L)
                .name("wallet1")
                .balance(new BigDecimal(4232))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        assertThatThrownBy(() -> walletService.deleteById(1L))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Impossible to delete this wallet. Wallet not found with id " + wallet.getId());
    }

    @Test
    public void walletUpdatedSuccesfullyTest() {
        WalletDto walletToChange = WalletDto.builder()
                .id(1L)
                .name("wallet1")
                .balance(new BigDecimal(2000))
                .defaultWallet(false)
                .currency(Currency.EUR)
                .userId(1L)
                .build();
        WalletDto updateWallet = WalletDto.builder()
                .id(1L)
                .name("wallet2")
                .balance(new BigDecimal(123))
                .defaultWallet(true)
                .currency(Currency.PLZ)
                .userId(1L)
                .build();
        UserDto walletOwner = UserDto.builder()
                .id(1L)
                .name("Aleks")
                .surname("Palos")
                .username("Petir")
                .password("qwerty123")
                .roles(new HashSet<>())
                .wallets(new HashSet<>())
                .build();
        Mockito.when(mockWalletRepository.findById(1L)).thenReturn(Optional.of(walletToChange));
        Mockito.when(mockUserRepository.findByUsername("Petir")).thenReturn(Optional.of(walletOwner));
        WalletDto changedWallet = walletService.updateWalletById(1L, updateWallet, "Petir");
        assertEquals(walletToChange.getName(), changedWallet.getName());
        assertEquals(walletToChange.isDefaultWallet(), changedWallet.isDefaultWallet());
        assertEquals(walletToChange.getCurrency(), changedWallet.getCurrency());
    }

    @Test
    public void WalletStatusChangedToNonDefault() {
        WalletDto walletToChange = WalletDto.builder()
                .id(1L)
                .name("wallet1")
                .balance(new BigDecimal(2000))
                .defaultWallet(false)
                .currency(Currency.EUR)
                .userId(1L)
                .build();
        WalletDto controlWallet = WalletDto.builder()
                .id(2L)
                .name("wallet2")
                .balance(new BigDecimal(1300))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        WalletDto updateWallet = WalletDto.builder()
                .id(1L)
                .name("wallet23")
                .balance(new BigDecimal(1200))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        UserDto walletOwner = UserDto.builder()
                .id(1L)
                .name("Aleks")
                .surname("Palos")
                .username("Petir")
                .password("qwerty123")
                .roles(new HashSet<>())
                .wallets(new HashSet<>())
                .build();
        Mockito.when(mockWalletRepository.findByStatus(updateWallet.isDefaultWallet(), walletOwner.getId()))
                .thenReturn(Optional.of(controlWallet));
        Mockito.when(mockWalletRepository.findById(1L))
                .thenReturn(Optional.of(walletToChange));
        Mockito.when(mockUserRepository.findByUsername("Petir")).thenReturn(Optional.of(walletOwner));
        WalletDto changedWallet = walletService.updateWalletById(1L, updateWallet, "Petir");
        assertFalse(controlWallet.isDefaultWallet());
        assertEquals(changedWallet.isDefaultWallet(), updateWallet.isDefaultWallet());

    }

    @Test
    public void notWalletOwner() {
        WalletDto walletToChange = WalletDto.builder()
                .id(1L)
                .name("wallet1")
                .balance(new BigDecimal(2000))
                .defaultWallet(false)
                .currency(Currency.EUR)
                .userId(1L)
                .build();
        WalletDto updateWallet = WalletDto.builder()
                .id(1L)
                .name("wallet2")
                .balance(new BigDecimal(1300))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        UserDto walletOwner = UserDto.builder()
                .id(1L)
                .name("Aleks")
                .surname("Palos")
                .username("Petir")
                .password("qwerty123")
                .roles(new HashSet<>())
                .wallets(new HashSet<>())
                .build();
        UserDto notWalletOwner = UserDto.builder()
                .id(2L)
                .name("Artsiom")
                .surname("Palos")
                .username("Artist")
                .password("qwerty123")
                .roles(new HashSet<>())
                .wallets(new HashSet<>())
                .build();
        Mockito.when(mockUserRepository.findByUsername("Artist")).thenReturn(Optional.of(notWalletOwner));
        Mockito.when(mockUserRepository.findByUsername("Petir")).thenReturn(Optional.of(walletOwner));
        Mockito.when(mockWalletRepository.findById(1L)).thenReturn(Optional.of(walletToChange));
        assertThatThrownBy(() -> walletService.updateWalletById(1L, updateWallet, "Artist"))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Wallet with  id = " + walletToChange.getId() + " not found");

    }
}