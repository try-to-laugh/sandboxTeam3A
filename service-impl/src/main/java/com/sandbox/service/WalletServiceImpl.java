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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {
    private static final Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public void deleteById(Long id, String username) {
        Optional<UserDto> userFoundedById = userRepository.findByUsername(username);
        Long walletOwnerId = userFoundedById.get().getId();
        WalletDto walletWhichWantToDelete = walletRepository.findById(id).orElseThrow(() ->
                new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id));
        if (walletWhichWantToDelete.getUserId() != walletOwnerId) {
            throw new WalletNotFoundException("Impossible to delete this wallet. Wallet not found with id " + id);
        }
        if (walletWhichWantToDelete.isDefaultWallet()) {
            Optional<UserDto> walletOwner = userRepository.findById(walletOwnerId);
            walletOwner.ifPresent(userDto -> changeDefaultWallet(userDto, id));
        }
        walletRepository.deleteById(id);

        LOG.info("The wallet was successfully deleted");
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
