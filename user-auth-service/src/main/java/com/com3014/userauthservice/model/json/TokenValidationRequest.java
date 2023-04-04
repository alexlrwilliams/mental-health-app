package com.com3014.userauthservice.model.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class TokenValidationRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String token;

    public TokenValidationRequest(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
