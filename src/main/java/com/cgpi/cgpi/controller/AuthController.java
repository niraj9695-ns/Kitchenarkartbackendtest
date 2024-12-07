//
//package com.cgpi.cgpi.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import com.cgpi.cgpi.entity.Admin;
//import com.cgpi.cgpi.repository.AdminRepository;
//import com.cgpi.cgpi.services.EmailService;
//import com.cgpi.cgpi.services.JwtTokenProvider;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @PostMapping("/register")
//    public String registerAdmin(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
//        if (adminRepository.findByUsername(username).isPresent()) {
//            return "Username already exists.";
//        }
//
//        Admin admin = new Admin();
//        admin.setUsername(username);
//        admin.setPassword(passwordEncoder.encode(password));
//        admin.setEmail(email);
//        admin.setResetToken(null);
//
//        adminRepository.save(admin);
//        return "Admin registered successfully.";
//    }
//
//    @PostMapping("/forgot-password")
//    public String forgotPassword(@RequestParam String email) {
//        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
//        if (adminOptional.isPresent()) {
//            Admin admin = adminOptional.get();
//            String resetToken = UUID.randomUUID().toString();
//            admin.setResetToken(resetToken);
//            adminRepository.save(admin);
//
//            String resetLink = "http://localhost:8080/auth/reset-password?token=" + resetToken;
//            String subject = "Password Reset Request";
//            String body = "To reset your password, please click the link below:\n" + resetLink;
//            emailService.sendEmail(admin.getEmail(), subject, body);
//
//            return "Password reset link sent to email.";
//        } else {
//            return "Admin with this email does not exist.";
//        }
//    }
//
//    @PostMapping("/reset-password")
//    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
//        Optional<Admin> adminOptional = adminRepository.findByResetToken(token);
//        if (adminOptional.isPresent()) {
//            Admin admin = adminOptional.get();
//            admin.setPassword(passwordEncoder.encode(newPassword));
//            admin.setResetToken(null);
//            adminRepository.save(admin);
//            return "Password has been reset successfully.";
//        } else {
//            return "Invalid reset token.";
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//            );
//
//            String token = jwtTokenProvider.generateToken(authentication);
//
//            return ResponseEntity.ok(new LoginResponse(token));
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
//    }
//}
//
package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.entity.Admin;
import com.cgpi.cgpi.repository.AdminRepository;
import com.cgpi.cgpi.services.JwtTokenProvider;

import java.util.Optional;
import java.util.UUID;

// Import PasswordEncoder class if necessary
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder; // Import PasswordEncoder
import org.springframework.web.bind.annotation.*;

import com.cgpi.cgpi.services.AdminDetailsService;
import com.cgpi.cgpi.services.EmailService;
import com.cgpi.cgpi.services.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;  // PasswordEncoder bean to encode passwords
    @Autowired
    private EmailService emailService;
    @Autowired
    private AdminDetailsService userDetailsService; // Or whatever service you use to load user details


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
        try {
            // Check if login is an email or username
            String usernameOrEmail = login.contains("@") ? "email" : "username";

            // Load user by username or email
            UserDetails userDetails;
            if (usernameOrEmail.equals("email")) {
                userDetails = userDetailsService.loadUserByEmail(login); // Implement method to fetch user by email
            } else {
                userDetails = userDetailsService.loadUserByUsername(login); // Implement method to fetch user by username
            }

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password)
            );

            // Generate JWT token
            String token = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new LoginResponse(token));  // Return the JWT token
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


    @PostMapping("/register")
    public String registerAdmin(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        if (adminRepository.findByUsername(username).isPresent()) {
            return "Username already exists.";
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));  // Encode the password
        admin.setEmail(email);
        admin.setResetToken(null);

        adminRepository.save(admin);
        return "Admin registered successfully.";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            String resetToken = UUID.randomUUID().toString();
            admin.setResetToken(resetToken);
            adminRepository.save(admin);

            String resetLink = "http://localhost:8080/auth/reset-password?token=" + resetToken;
            String subject = "Password Reset Request";
            String body = "To reset your password, please enter the token given below in the reset password \n" + resetLink;
            emailService.sendEmail(admin.getEmail(), subject, body);

            return "Password reset link sent to email.";
        } else {
            return "Admin with this email does not exist.";
        }
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<Admin> adminOptional = adminRepository.findByResetToken(token);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setPassword(passwordEncoder.encode(newPassword));
            admin.setResetToken(null);
            adminRepository.save(admin);
            return "Password has been reset successfully.";
        } else {
            return "Invalid reset token.";
        }
    }
    // Additional methods like forgot-password, reset-password can be added here
}
