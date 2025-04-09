package com.cgpi.cgpi.services;



import com.cgpi.cgpi.DTO.UserDTO;
import com.cgpi.cgpi.DTO.LoginRequest;
import com.cgpi.cgpi.DTO.ResetPasswordRequest;
import com.cgpi.cgpi.entity.User;
import com.cgpi.cgpi.repository.UserRepository;
import com.cgpi.cgpi.response.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private EmailServices emailServices;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
   

    private final Map<String, String> otpCache = new HashMap<>();

    @Override
    public SuccessResponse registerUser(UserDTO userDTO) {
        SuccessResponse response = new SuccessResponse();

        if (userDTO.getUsername() == null || userDTO.getEmail() == null || userDTO.getPassword() == null) {
            response.setNullFields();
            return response;
        }

        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            response.setAlreadyExists();
            return response;
        }

        User newUser = modelMapper.map(userDTO, User.class);
        userRepository.save(newUser);
        response.setUserCreated(newUser);
        return response;
    }


   @Override
    public SuccessResponse loginUser(@RequestBody LoginRequest loginRequest) {
        SuccessResponse response = new SuccessResponse();

        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            response.setSuccess("Login successful", user.get());  // ✅ Correct success response
        } else {
            response.setError("Invalid credentials");  // ✅ Ensure failure response
        }

        return response;
    }



    @Override
    public SuccessResponse sendOtp(String email) {
        SuccessResponse response = new SuccessResponse();

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String otp = generateOtp();
            otpCache.put(email, otp);

            // Send the email
            emailServices.sendOtpEmail(email, Integer.parseInt(otp));
            
            response.setSuccess("OTP sent successfully. " , null);
        } else {
            response.setError("Email not registered");
        }

        return response;
    }

    @Override
    public SuccessResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        SuccessResponse response = new SuccessResponse();

        String email = resetPasswordRequest.getEmail();
        int otp = resetPasswordRequest.getOtp();
        String newPassword = resetPasswordRequest.getNewPassword();

        if (!otpCache.containsKey(email) || !otpCache.get(email).equals(String.valueOf(otp))) {
            response.setError("Invalid OTP");
            return response;
        }

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User u = user.get();
            u.setPassword(newPassword);
            userRepository.save(u);
            response.setSuccess("Password reset successfully", u);
        } else {
            response.setError("User not found");
        }

        otpCache.remove(email);
        return response;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public SuccessResponse updateUserDetails(UserDTO userDTO) {
        SuccessResponse response = new SuccessResponse();

        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userDTO.getUsername());
            user.setAddress(userDTO.getAddress());
            user.setState(userDTO.getState());
            user.setCountry(userDTO.getCountry());
            user.setPincode(userDTO.getPincode());
            user.setContactNumber(userDTO.getContactNumber());
            userRepository.save(user);
            response.setUserUpdated(user);
        } else {
            response.setUserNotFound();
        }

        return response;
    }

    @Override
    public SuccessResponse deleteUserByAdmin(Long userId) {
        SuccessResponse response = new SuccessResponse();
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            response.setSuccess("User deleted successfully", null);
        } else {
            response.setError("User not found");
        }
        return response;
    }

    @Override
    public SuccessResponse findUserByUsername(String username) {
        SuccessResponse response = new SuccessResponse();
        
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            response.setSuccess("User found", user.get());
        } else {
            response.setError("User not found");
        }

        return response;
    }
   
    

    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
    
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));
    }
//    
    
    
    
}