package com.sandbox.service;

import com.sandbox.Role;
import com.sandbox.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();

    Optional<User> findUserByEmail(String email);

    void saveUser(User user);

    void addRoleToUser(User user, Role role);

    void encodeAndSetPasswordToUser(User user, PasswordEncoder passwordEncoder);
}
