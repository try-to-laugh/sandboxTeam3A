package com.sandbox.repository;

import com.sandbox.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoryJpa extends JpaRepository<Category, Long> {
}
