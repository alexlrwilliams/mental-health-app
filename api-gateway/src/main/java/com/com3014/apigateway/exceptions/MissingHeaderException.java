package com.com3014.apigateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Missing JWT Token")
public class MissingHeaderException extends RuntimeException {
    public MissingHeaderException(String errorMessage) {
        super(errorMessage);
    }
}