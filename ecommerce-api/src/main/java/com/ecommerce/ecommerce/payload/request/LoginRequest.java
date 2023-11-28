package com.ecommerce.ecommerce.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username/email is required")
    private String usernameOrEmail;
    @NotBlank(message = "Password is required")
    private String password;
}