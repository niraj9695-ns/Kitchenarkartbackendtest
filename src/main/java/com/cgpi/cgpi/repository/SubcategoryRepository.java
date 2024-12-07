package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.Subcategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
	Subcategory findByName(String name);
	List<Subcategory> findByCategoryId(Long categoryId);

}
