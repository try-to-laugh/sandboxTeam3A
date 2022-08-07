package com.sandbox.rest;

import com.sandbox.api.UsersApi;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthenticationRestController implements UsersApi {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
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
    public ResponseEntity<Void> logout() {
        return null;
    }

    @PostMapping(path = "/registration")
    @PermitAll
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.saveUser(userService.addRoleToUser(userService.encodeAndSetPasswordToUser(userDto, passwordEncoder),
                roleService.findRoleByName("USER")));
        return ResponseEntity.ok().body("Registration was successful");
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
