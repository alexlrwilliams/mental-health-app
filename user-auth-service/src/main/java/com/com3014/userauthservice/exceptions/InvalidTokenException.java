package com.com3014.userauthservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid token")
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String errorMessage) {
        super(errorMessage);
    }
}
