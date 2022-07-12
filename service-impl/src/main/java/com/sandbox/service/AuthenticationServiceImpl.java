package com.sandbox.service;

import com.sandbox.AuthenticationRequestDTO;
import com.sandbox.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Override
    public User authenticateUserAndGetToken(AuthenticationRequestDTO request) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
        return userService.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
    }
}
