package com.sandbox.service;

import com.sandbox.AuthenticationRequestDTO;
import com.sandbox.User;
import org.springframework.security.core.AuthenticationException;


public interface AuthenticationService {

    User authenticateUserAndGetToken (AuthenticationRequestDTO request) throws AuthenticationException;

}
