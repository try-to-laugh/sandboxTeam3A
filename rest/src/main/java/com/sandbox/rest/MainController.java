package com.sandbox.rest;

import com.sandbox.dto.UserDto;
import com.sandbox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/budget")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    public String sayHello() {
        return "hello world";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers();
    }
}
