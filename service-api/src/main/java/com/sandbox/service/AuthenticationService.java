package com.sandbox.service;

import com.sandbox.dto.UserDto;

public interface AuthenticationService {

    UserDto authenticateUserAndGetToken(UserDto request);
}
