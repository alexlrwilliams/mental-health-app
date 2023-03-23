package com.com3014.userauthservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists")
public class UserAlreadyExistAuthenticationException extends RuntimeException {
    public UserAlreadyExistAuthenticationException(String errorMessage) {
        super(errorMessage);
    }
}
