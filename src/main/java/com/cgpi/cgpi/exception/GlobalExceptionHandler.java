package com.cgpi.cgpi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Log the exception details (you might want to use a logging framework instead of e.printStackTrace())
        e.printStackTrace();
        return new ResponseEntity<>("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
