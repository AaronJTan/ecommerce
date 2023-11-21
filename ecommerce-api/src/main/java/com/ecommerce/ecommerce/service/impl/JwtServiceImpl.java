package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMS}")
    private int jwtExpirationMs;

    @Override
    public String generateJwtToken(User user) {
        Key signingKey = getSigningKey();

        List<String> roles = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(String.valueOf(user.getId()))
                .claim("name", user.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    @Override
    public boolean validateJwt(String jwt) {
        Key signingKey= getSigningKey();
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parse(jwt);
            return true;
        } catch (MalformedJwtException e) {
        } catch (SignatureException e) {
        } catch (ExpiredJwtException e) {
        } catch (IllegalArgumentException e) {
        }

        return false;
    }
}
