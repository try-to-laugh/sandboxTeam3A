package com.sandbox.repository;

import com.sandbox.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepositoryJpa extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
