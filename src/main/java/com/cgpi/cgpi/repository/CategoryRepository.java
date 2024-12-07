package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
	// Custom query to find categories marked as top categories
    List<Category> findByTopCategoryTrue();
}
