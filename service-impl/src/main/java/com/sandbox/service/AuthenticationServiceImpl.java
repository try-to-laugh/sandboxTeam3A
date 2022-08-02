package com.sandbox.service;

import com.sandbox.dto.UserDto;
import com.sandbox.exception.UnauthorizedException;
import com.sandbox.model.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Override
    public UserDto authenticateUserAndGetToken(UserLoginDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
        } catch (AuthenticationException e){
            throw new UnauthorizedException("Invalid Email or Password");
        }
        return userService.findUserByEmail(request.getUsername());
    }
}
