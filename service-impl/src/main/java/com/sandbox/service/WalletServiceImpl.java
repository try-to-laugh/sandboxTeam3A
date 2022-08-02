package com.sandbox.service;

import com.sandbox.UserDetailsImpl;
import com.sandbox.dto.WalletDto;
import com.sandbox.entities.User;
import com.sandbox.entities.Wallet;
import com.sandbox.mappers.WalletMapper;
import com.sandbox.repository.UserRepository;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final UserRepository userRepository;

    @Override
    public Wallet createWallet(WalletDto walletDto){
        UserDetailsImpl userDetails =(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).get();
        if(user.getWallets().isEmpty()) {
            walletDto.setDefault(true);
        }
        Wallet walletToCreate = walletMapper.toWallet(walletDto);
        walletToCreate.setUser(user);
        return walletRepository.save(walletToCreate);
    }
}
