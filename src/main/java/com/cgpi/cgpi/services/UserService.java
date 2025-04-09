package com.cgpi.cgpi.services;



import com.cgpi.cgpi.DTO.UserDTO;
import com.cgpi.cgpi.DTO.LoginRequest;
import com.cgpi.cgpi.DTO.ResetPasswordRequest;
import com.cgpi.cgpi.entity.User;
import com.cgpi.cgpi.response.SuccessResponse;

import java.util.List;

public interface UserService {
    SuccessResponse registerUser(UserDTO userDTO);
    SuccessResponse loginUser(LoginRequest loginRequest);
    SuccessResponse sendOtp(String email);
    SuccessResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
    List<User> getAllUsers();
    SuccessResponse updateUserDetails(UserDTO userDTO);
    SuccessResponse deleteUserByAdmin(Long userId);
    SuccessResponse findUserByUsername(String username);
//    SuccessResponse getUserById(Long userId);

User getUserById(Long userId);
    
}