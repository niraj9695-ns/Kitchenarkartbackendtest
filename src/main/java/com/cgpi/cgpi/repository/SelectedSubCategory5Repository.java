package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.SelectedSubCategory5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Indicates that this interface is a repository
public interface SelectedSubCategory5Repository extends JpaRepository<SelectedSubCategory5, Long> {
    // Inherits CRUD methods (findAll, save, delete) from JpaRepository
}
