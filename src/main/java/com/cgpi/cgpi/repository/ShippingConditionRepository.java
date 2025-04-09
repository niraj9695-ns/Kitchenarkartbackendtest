package com.cgpi.cgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cgpi.cgpi.entity.ShippingCondition;

@Repository
public interface ShippingConditionRepository extends JpaRepository<ShippingCondition, Long> {
    List<ShippingCondition> findByRegionAndActive(String region, Boolean active);
    @Query("SELECT DISTINCT s.region FROM ShippingCondition s WHERE s.region IS NOT NULL")
    List<String> findDistinctRegions();

}
