package com.sandbox.repository;

import com.sandbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long userId);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long idUser);
}
