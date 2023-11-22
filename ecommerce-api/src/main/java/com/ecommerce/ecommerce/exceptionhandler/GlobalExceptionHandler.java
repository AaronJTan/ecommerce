package com.ecommerce.ecommerce.exceptionhandler;

import com.ecommerce.ecommerce.constants.ResponseMessage;
import com.ecommerce.ecommerce.exception.*;
import com.ecommerce.ecommerce.payload.response.ApiResponse;
import com.ecommerce.ecommerce.payload.response.ResponseEntityBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ApiResponse> handleUserRegistrationException(UserRegistrationException ex) {
        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.CONFLICT)
                .setMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.UNAUTHORIZED)
                .setMessage(ResponseMessage.INVALID_CREDENTIALS)
                .build();
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(MissingRequestHeaderException ex) {
        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.UNAUTHORIZED)
                .setMessage(String.format("%s header missing.", ex.getHeaderName()))
                .build();
    }

    @ExceptionHandler(DoesNotExistException.class)
    public ResponseEntity<ApiResponse> handleDoesNotExistException(DoesNotExistException ex) {
        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<ApiResponse> handleInvalidFileTypeException(InvalidFileTypeException ex) {
        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<ApiResponse> handleNoSuchFileException(NoSuchFileException ex) {
        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.NOT_FOUND)
                .setMessage("File does not exist.")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setError(errors)
                .build();
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExistsException(AlreadyExistsException ex) {
        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.CONFLICT)
                .setMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage(ex.getMessage())
                .build();
    }
}