package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payload.request.LoginRequest;
import com.ecommerce.ecommerce.payload.request.SignupRequest;

public interface AuthenticationService {
    void registerUser(SignupRequest signUpRequest);
    String authenticateUser(LoginRequest loginRequest);
    boolean validateJWT(String jwt);
}
