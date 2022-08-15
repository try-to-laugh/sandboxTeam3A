import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.dto.RoleDto;
import com.sandbox.enums.Currency;
import com.sandbox.exception.BudgetRuntimeException;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.UserRepository;
import com.sandbox.repository.WalletRepository;
import com.sandbox.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;

import org.mockito.Captor;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Set;
import  java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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

    @Captor
    ArgumentCaptor<WalletDto> argCaptor;

    private final WalletDto walletToChange = WalletDto.builder()
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

    @Test
    void deleteById() {
        Set<RoleDto> roles = new HashSet<>();
        roles.add(new RoleDto(1L, "Admin"));
        Set<WalletDto> wallets = new HashSet<>();

        UserDto owner = new UserDto(1L, "John", "Bulon", "johnbullon", "123rhfjcdswe", roles, wallets);
        WalletDto wallet = new WalletDto(1L, "w1", new BigDecimal(122), true, Currency.EUR, owner.getId());
        wallets.add(wallet);
        Mockito.doAnswer(delete -> {
                    String deleted = "deleted";
                    assertEquals("deleted", deleted);
                    return null;
                }
        ).when(mockWalletRepository).deleteById(1L);
        Mockito.when(mockUserRepository.findByUsername("johnbullon")).thenReturn(Optional.of(owner));
        Mockito.when(mockWalletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        walletService.deleteById(1L, owner.getUsername());
        verify(mockWalletRepository).deleteById(1L);
    }

    @Test
    void walletNotExistForDeletingTest() {
        Set<RoleDto> roles = new HashSet<>();
        roles.add(new RoleDto(1L, "Admin"));
        Set<WalletDto> wallets = new HashSet<>();

        UserDto owner = new UserDto(1L, "John", "Bulon", "johnbullon", "123rhfjcdswe", roles, wallets);
        Mockito.when(mockUserRepository.findByUsername(owner.getUsername())).thenReturn(Optional.of(owner));
        assertThatThrownBy(() -> walletService.deleteById(2L, "johnbullon"))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Impossible to delete this wallet. Wallet not found with id " + 2L);
    }

    @Test
    void changeDefaultWallet() {
        Set<WalletDto> wallets = new HashSet<>();
        Set<RoleDto> roles = new HashSet<>();
        UserDto owner = new UserDto(1L, "John", "Bulon", "johnbullon", "123rhfjcdswe", roles, wallets);
        WalletDto walletDefault = new WalletDto(1L, "w1", new BigDecimal(122), true, Currency.EUR, owner.getId());
        WalletDto walletNotDefault = new WalletDto(2L, "w2", new BigDecimal(3000), false, Currency.EUR, owner.getId());
        WalletDto walletNotDefaultSecond = new WalletDto(3L, "w3", new BigDecimal(320), false, Currency.EUR, owner.getId());
        wallets.add(walletDefault);
        wallets.add(walletNotDefault);
        wallets.add(walletNotDefaultSecond);
        Mockito.when(mockUserRepository.findByUsername(owner.getUsername())).thenReturn(Optional.of(owner));
        Mockito.when(mockWalletRepository.findById(walletDefault.getId())).thenReturn(Optional.of(walletDefault));
        Mockito.when(mockUserRepository.findById(walletDefault.getUserId())).thenReturn(Optional.of(owner));
        Mockito.when(mockWalletRepository.findWalletWithMaxBalance(owner.getId(),walletDefault.getId())).thenReturn(Optional.of(walletNotDefault));
        walletService.deleteById(walletDefault.getId(), owner.getUsername());

        verify(mockWalletRepository, times(1)).save(argCaptor.capture());

        WalletDto savedWallet = argCaptor.getValue();
        assertEquals(walletNotDefault, savedWallet);
    }

    @Test
    void walletUpdatedSuccessfullyTest() {
        WalletDto updateWallet = WalletDto.builder()
                .id(1L)
                .name("wallet2")
                .balance(new BigDecimal(123))
                .defaultWallet(true)
                .currency(Currency.PLZ)
                .userId(1L)
                .build();
        Mockito.when(mockWalletRepository.findById(1L)).thenReturn(Optional.of(walletToChange));
        Mockito.when(mockUserRepository.findByUsername("Petir")).thenReturn(Optional.of(walletOwner));
        WalletDto changedWallet = walletService.updateWalletById(1L, updateWallet, "Petir");
        assertEquals(walletToChange.getName(), changedWallet.getName());
        assertEquals(walletToChange.isDefaultWallet(), changedWallet.isDefaultWallet());
        assertEquals(walletToChange.getCurrency(), changedWallet.getCurrency());
    }

    @Test
    void WalletStatusChangedToNonDefault() {
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
    void notWalletOwner() {
        WalletDto updateWallet = WalletDto.builder()
                .id(1L)
                .name("wallet2")
                .balance(new BigDecimal(1300))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
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

    @Test
    void getWalletByIdTest() {
        Mockito.when(mockWalletRepository.findById(1L)).thenReturn(Optional.of(walletToChange));
        WalletDto actualWallet = walletService.getWalletById(1L);
        assertEquals(walletToChange, actualWallet);
    }

    @Test
    void walletNotFoundGetByIdTest() {
        assertThatThrownBy(() -> walletService.getWalletById(20L))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("This wallet not found");
    }

    @Test
    public void createNonDefaulWalletForUserWithoutWalletsTest() {
        UserDto user = UserDto.builder()
                .id(1L)
                .name("Harry")
                .surname("Potter")
                .username("username")
                .password("password")
                .roles(new HashSet<>())
                .wallets(new HashSet<>())
                .build();
        WalletDto walletToAdd = WalletDto.builder()
                .name("super")
                .balance(new BigDecimal(1300))
                .defaultWallet(false)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        Mockito.when(mockUserRepository.findByUsername(any())).thenReturn(Optional.of(user));
        Mockito.when(mockWalletRepository.save(any())).thenReturn(1L);
        walletService.createWallet(walletToAdd, user.getUsername());
        assertTrue(walletToAdd.isDefaultWallet());
    }

    @Test
    public void createDefaulWalletForUserWithAlreadyExistedDefaultWalletTest() {
        UserDto user = UserDto.builder()
                .id(1L)
                .name("Harry")
                .surname("Potter")
                .username("username")
                .password("password")
                .roles(new HashSet<>())
                .wallets(new HashSet<>())
                .build();
        WalletDto walletExisted = WalletDto.builder()
                .name("wallet1")
                .balance(new BigDecimal(1300))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        WalletDto walletToAdd = WalletDto.builder()
                .name("super")
                .balance(new BigDecimal(0))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        user.getWallets().add(walletExisted);
        Mockito.when(mockUserRepository.findByUsername(any())).thenReturn(Optional.of(user));
        Mockito.when(mockWalletRepository.save(any())).thenReturn(1L);
        walletService.createWallet(walletToAdd, user.getUsername());
        assertTrue(walletToAdd.isDefaultWallet());
        assertFalse(walletExisted.isDefaultWallet());
    }

    @Test
    public void createWalletTheSameWalletAlreadyExistTest() {
        UserDto user = UserDto.builder()
                .id(1L)
                .name("Harry")
                .surname("Potter")
                .username("username")
                .password("password")
                .roles(new HashSet<>())
                .wallets(new HashSet<>())
                .build();
        WalletDto walletExisted = WalletDto.builder()
                .name("wallet1")
                .balance(new BigDecimal(0))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        WalletDto walletToAdd = WalletDto.builder()
                .name("wallet1")
                .balance(new BigDecimal(0))
                .defaultWallet(true)
                .currency(Currency.USD)
                .userId(1L)
                .build();
        user.getWallets().add(walletExisted);
        Mockito.when(mockUserRepository.findByUsername(any())).thenReturn(Optional.of(user));
        Mockito.when(mockWalletRepository.save(any())).thenReturn(1L);
        assertThatThrownBy(() -> walletService.createWallet(walletToAdd, user.getUsername())).isInstanceOf(
                BudgetRuntimeException.class).hasMessage("Such wallet already exists, please choose another currency or change the name of wallet");
    }
}
