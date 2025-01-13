package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.SelectedCategory2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Indicates that this interface is a repository
public interface SelectedCategory2Repository extends JpaRepository<SelectedCategory2, Long> {
    // Inherits CRUD methods (findAll, save, delete) from JpaRepository
}
