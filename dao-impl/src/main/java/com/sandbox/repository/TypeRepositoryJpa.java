package com.sandbox.repository;

import com.sandbox.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepositoryJpa extends JpaRepository<Type, Long> {
    Optional<Type> findById(Long id);

    Optional<Type> findByName(String name);
}
