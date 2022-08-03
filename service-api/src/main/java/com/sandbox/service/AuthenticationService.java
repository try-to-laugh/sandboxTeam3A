package com.sandbox.service;

import com.sandbox.dto.UserDto;
import com.sandbox.model.UserLoginDto;

public interface AuthenticationService {
    UserDto authenticateUserAndGetToken(UserLoginDto request);

}
