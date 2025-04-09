package com.cgpi.cgpi.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cgpi.cgpi.DTO.LoginRequest;
import com.cgpi.cgpi.DTO.ResetPasswordRequest;
import com.cgpi.cgpi.DTO.UserDTO;
import com.cgpi.cgpi.entity.User;
import com.cgpi.cgpi.response.SuccessResponse;
import com.cgpi.cgpi.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public SuccessResponse registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public SuccessResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @PostMapping("/sendotp")
    public SuccessResponse sendOtp(@RequestParam String email) {
        return userService.sendOtp(email);
    }

    @PostMapping("/resetpassword")
    public SuccessResponse resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return userService.resetPassword(resetPasswordRequest);
    }

    @GetMapping("/all")  // New mapping to get all users
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/update")  // New mapping to update user details
    public SuccessResponse updateUserDetails(@RequestBody UserDTO userDTO) {
        return userService.updateUserDetails(userDTO);
    }

    @GetMapping("/find/{username}")  // New mapping to find user by username
    public SuccessResponse findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }
   
  
    @GetMapping("/{userId}")  // New mapping to get user by ID
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
    

    @DeleteMapping("/delete/{userId}")
    public SuccessResponse deleteUserByAdmin(@PathVariable Long userId) {
        return userService.deleteUserByAdmin(userId);
    }
}