package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.Subsubcategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsubcategoryRepository extends JpaRepository<Subsubcategory, Long> {
	Subsubcategory findByName(String name);
	public List<Subsubcategory> findBySubcategoryId(Long subcategoryId);

}
