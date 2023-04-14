package com.com3014.userauthservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User not valid")
public class UserNotValidException extends RuntimeException {
    public UserNotValidException(String errorMessage) {
        super(errorMessage);
    }
}
