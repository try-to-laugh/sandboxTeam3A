package com.sandbox.rest;

import com.sandbox.api.UsersApi;
import com.sandbox.config.LogOutCacheConfiguration;
import com.sandbox.dto.UserDto;
import com.sandbox.config.jwt.JwtTokenProvider;
import com.sandbox.model.UserLoginDto;
import com.sandbox.model.UserResponseDto;
import com.sandbox.service.AuthenticationService;
import com.sandbox.service.RoleService;
import com.sandbox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UsersController implements UsersApi {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final LogOutCacheConfiguration logOutCacheConfiguration;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Override
    public ResponseEntity<Void> deleteUserById(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDto> getUserById(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> login(@Valid UserLoginDto userLoginDto) {
        UserDto userDto = authenticationService.authenticateUserAndGetToken(userLoginDto);
        String token = jwtTokenProvider.createToken(userLoginDto.getUsername(), userService.getUserRoles(userDto));
        return ResponseEntity.ok().header(authorizationHeader, "Bearer " + token).build();
    }

    @Override
    public ResponseEntity<Void> logout(String authorization) {
        logOutCacheConfiguration.banJwt(authorization, authorization);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/registration")
    @PermitAll
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.saveUser(userService.addRoleToUser(userService.encodeAndSetPasswordToUser(userDto, passwordEncoder),
                roleService.findRoleByName("USER")));
        return ResponseEntity.ok().body("Registration was successful");
    }
}
