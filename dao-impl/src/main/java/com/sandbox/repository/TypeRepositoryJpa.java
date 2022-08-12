package com.sandbox.repository;

import com.sandbox.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepositoryJpa extends JpaRepository<Type, Long> {
}
