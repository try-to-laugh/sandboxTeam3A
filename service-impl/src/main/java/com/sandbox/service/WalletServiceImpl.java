package com.sandbox.service;


import com.sandbox.dto.WalletDto;
import com.sandbox.entity.Wallet;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.exception.WalletWithSameNameAndCurrencyExist;
import com.sandbox.mappers.WalletMapper;
import com.sandbox.model.WalletRequestDto;
import com.sandbox.model.WalletResponseDto;
import com.sandbox.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;


    @Override
    public WalletResponseDto updateWalletById(Long walletId, WalletRequestDto walletRequestDto) {
        try {
            Wallet updatedWallet = walletRepository.findById(walletId)
                    .orElseThrow(() -> new WalletNotFoundException("wallet with  id = " + walletId + " not found'"));

            WalletResponseDto responseWallet = walletMapper.fromWalletToWalletResponseDto(updatedWallet);

            Optional<Wallet> defaultStatusWalletCheck = walletRepository.findByDefault(walletRequestDto.getDefault());
            if (defaultStatusWalletCheck.isPresent()) {
                Wallet makeFalseWallet = defaultStatusWalletCheck.get();
                makeFalseWallet.setDefault(false);
                walletRepository.save(makeFalseWallet);
            }

            responseWallet.setName(walletRequestDto.getName());
            responseWallet.setCurrency(walletRequestDto.getCurrency());
            responseWallet.setDefault(walletRequestDto.getDefault());

            updatedWallet = walletMapper.fromWalletResponseDtoToWallet(responseWallet);

            walletRepository.saveAndFlush(updatedWallet);

            return responseWallet;
        } catch (DataIntegrityViolationException ex) {
            throw new WalletWithSameNameAndCurrencyExist("A wallet with such a name and currency already exists. Please, change the name", ex);
        }

    }

}
