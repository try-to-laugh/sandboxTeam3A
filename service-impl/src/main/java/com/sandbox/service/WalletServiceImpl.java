package com.sandbox.service;

import com.sandbox.dto.UserDto;
import com.sandbox.dto.WalletDto;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.repository.UserRepository;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public void deleteById(Long id) {
        WalletDto walletWhichWantToDelete = walletRepository.findById(id)
                .orElseThrow(() ->
                        new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id "
                                + id));

        if (walletWhichWantToDelete.isDefaultWallet()) {
            Long walletOwnerId = walletWhichWantToDelete.getUserId();
            walletRepository.deleteById(id);
            changeDefaultWallet(walletOwnerId);
        }

        walletRepository.deleteById(id);

        LOG.info("The wallet was successfully deleted");
    }

    private void changeDefaultWallet(Long walletOwnerId) {
        UserDto walletOwner = userRepository.findByUserId(walletOwnerId).get();
        if (!walletOwner.getWallets().isEmpty()) {
            LOG.info("User had only one wallet and it was default");
            Optional<WalletDto> newDefaultWalletOptional = walletOwner.getWallets().stream()
                    .max(Comparator.comparing(WalletDto::getBalance));
            newDefaultWalletOptional.ifPresent(wallet -> wallet.setDefaultWallet(true));
            LOG.info("Default wallet successfully changed");
        }
    }

    public WalletDto updateWalletById(Long walletId, WalletDto requestWallet, String userName) {
        Long walletOwnerId = userRepository.findByUsername(userName).get().getId();
        WalletDto updatedWallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with  id = " + walletId + " not found'"));
        if (updatedWallet.getUserId() != walletOwnerId) {
            throw new WalletNotFoundException("Wallet with  id = " + walletId + " not found");
        }
        Optional<WalletDto> defaultStatusWalletCheck =
                walletRepository.findByStatus(requestWallet.isDefaultWallet(), walletOwnerId);
        if (defaultStatusWalletCheck.isPresent()) {
            WalletDto makeFalseWallet = defaultStatusWalletCheck.get();
            makeFalseWallet.setDefaultWallet(false);
            walletRepository.save(makeFalseWallet);
        }

        updatedWallet.setName(requestWallet.getName());
        updatedWallet.setCurrency(requestWallet.getCurrency());
        updatedWallet.setDefaultWallet(requestWallet.isDefaultWallet());

        walletRepository.save(updatedWallet);

        return updatedWallet;
    }

}
