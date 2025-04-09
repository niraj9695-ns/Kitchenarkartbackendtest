package com.cgpi.cgpi.response;

public class SuccessResponse {
    
    private boolean success;  // ✅ Added success field
    private String message;
    private Object data;

    // ✅ Set success response with message and data
    public void setSuccess(String message, Object data) {
        this.success = true; 
        this.message = message;
        this.data = data;
    }

    // ✅ Set error response (failure case)
    public void setError(String message) {
        this.success = false;
        this.message = message;
        this.data = null;  // Ensure data is null when an error occurs
    }

    // ✅ Set response when required fields are missing
    public void setNullFields() {
        this.success = false;
        this.message = "Required fields are missing";
        this.data = null;
    }

    // ✅ Set response when user already exists
    public void setAlreadyExists() {
        this.success = false;
        this.message = "User already exists";
        this.data = null;
    }

    // ✅ Set response when user is successfully registered
    public void setUserCreated(Object user) {
        this.success = true;
        this.message = "User registered successfully";
        this.data = user;
    }

    // ✅ Set response when user details are updated
    public void setUserUpdated(Object user) {
        this.success = true;
        this.message = "User details updated successfully";
        this.data = user;
    }

    // ✅ Set response when user is not found
    public void setUserNotFound() {
        this.success = false;
        this.message = "User not found";
        this.data = null;
    }

    // ✅ Getters and Setters
    public boolean isSuccess() {  
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
