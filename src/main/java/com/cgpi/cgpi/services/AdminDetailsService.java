package com.cgpi.cgpi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cgpi.cgpi.entity.Admin;
import com.cgpi.cgpi.repository.AdminRepository;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found with username: " + username));

        return org.springframework.security.core.userdetails.User.withUsername(admin.getUsername())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }

    // Add this method to load user by email
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));

        return org.springframework.security.core.userdetails.User.withUsername(admin.getUsername())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }
}
