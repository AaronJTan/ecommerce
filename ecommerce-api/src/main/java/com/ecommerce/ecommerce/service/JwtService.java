package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.User;

public interface JwtService {
    String generateJwtToken(User user);
    boolean validateJwt(String jwt);
}
