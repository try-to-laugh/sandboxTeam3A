package com.sandbox.repository;

import com.sandbox.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepositoryJpa extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query(value = "SELECT * FROM categories JOIN types ON categories.type_id = types.id WHERE types.name = :categoryType", nativeQuery = true)
    List<Category> findAll(@Param("categoryType") String categoryType);

    Optional<Category> findById(Long id);
}
