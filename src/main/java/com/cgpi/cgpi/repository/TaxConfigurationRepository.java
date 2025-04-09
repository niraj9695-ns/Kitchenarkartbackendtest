package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.TaxConfiguration;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxConfigurationRepository extends JpaRepository<TaxConfiguration, Long> {

    // Correct usage of Optional
    Optional<TaxConfiguration> findById(Long id); // Retrieve the tax configuration
}
