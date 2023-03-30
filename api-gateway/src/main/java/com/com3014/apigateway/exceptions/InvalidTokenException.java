package com.com3014.apigateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Invalid JWT Token")
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String errorMessage) {
        super(errorMessage);
    }
}