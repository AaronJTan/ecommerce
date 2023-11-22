package com.ecommerce.ecommerce.security;

import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtAuthService jwtAuthService;

    public JwtAuthenticationFilter(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> optionalJwt = extractToken(request);

        if (!optionalJwt.isEmpty()) {
            String jwt = optionalJwt.get();

            Claims claims = jwtAuthService.getJwtClaims(jwt);
            UsernamePasswordAuthenticationToken authentication = createUsernamePasswordAuthenticationToken(claims);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            return Optional.of(authenticationHeader.substring(7));
        }

        return Optional.empty();
    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(Claims claims) {
        long userid = Long.parseLong(claims.getSubject());
        String name = (String)claims.get("name");
        List<String> roles = (List<String>) claims.get("roles");
        JwtUser user =  new JwtUser(userid, name);

        return new UsernamePasswordAuthenticationToken(user,null,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}
