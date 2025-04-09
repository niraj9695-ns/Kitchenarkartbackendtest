package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.SelectedSubCategory2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Indicates that this interface is a repository
public interface SelectedSubCategory2Repository extends JpaRepository<SelectedSubCategory2, Long> {
    // Inherits CRUD methods (findAll, save, delete) from JpaRepository
}
