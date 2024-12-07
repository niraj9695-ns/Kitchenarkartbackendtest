package com.cgpi.cgpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgpi.cgpi.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByResetToken(String resetToken);
}

