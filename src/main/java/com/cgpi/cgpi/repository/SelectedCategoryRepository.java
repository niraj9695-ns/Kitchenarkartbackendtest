package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.SelectedCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Indicates that this interface is a repository
public interface SelectedCategoryRepository extends JpaRepository<SelectedCategory, Long> {
    // Inherits CRUD methods (findAll, save, delete) from JpaRepository
}
