package com.sandbox.rest;

import com.sandbox.AuthenticationRequestDTO;
import com.sandbox.User;
import com.sandbox.config.jwt.JwtTokenProvider;
import com.sandbox.service.AuthenticationService;
import com.sandbox.service.RoleService;
import com.sandbox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            User user = authenticationService.authenticateUserAndGetToken(request);
            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRoles());
            return ResponseEntity.ok().body(token);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/registration")
    @PermitAll
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.encodeAndSetPasswordToUser(user, passwordEncoder);
        userService.addRoleToUser(user, roleService.findRoleByName("USER").get());
        userService.saveUser(user);
        return ResponseEntity.ok().body("Registration was successful");
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
