package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.User;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateJwtToken(User user);
    boolean validateJwt(String jwt);
    Claims getJwtClaims(String jwt);
}
