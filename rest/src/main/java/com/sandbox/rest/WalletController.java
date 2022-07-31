package com.sandbox.rest;

import com.sandbox.entities.Currency;
import com.sandbox.entities.User;
import com.sandbox.entities.Wallet;
import com.sandbox.repository.UserRepository;
import com.sandbox.service.CurrencyService;
import com.sandbox.service.UserService;
import com.sandbox.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/budget/wallet")
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;
    private final CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<String> createWallet(@RequestBody Wallet wallet) {
        Wallet walletToSave = new Wallet();
        Optional<User> userOptional = userService.findUserById(wallet.getUser().getId());
        if(userOptional.isEmpty()) {
            return new ResponseEntity<String>("Invalid user id", HttpStatus.BAD_REQUEST);
        } else {
            walletToSave.setUser(userOptional.get());
        }

        Optional<Currency> currencyOptional = currencyService.findById(wallet.getCurrency().getId());
        if(currencyOptional.isEmpty()) {
            return new ResponseEntity<String>("Invalid currency id", HttpStatus.BAD_REQUEST);
        } else {
            walletToSave.setCurrency(currencyOptional.get());
        }
        walletToSave.setName(wallet.getName());
        walletToSave.setBalance(new BigDecimal(0));
        walletToSave.setDefault(wallet.isDefault());
        walletService.saveWallet(walletToSave);
        return new ResponseEntity<String>("New wallet created", HttpStatus.OK);
    }
}
