package com.com3014.userauthservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Unauthorized access")
public class UnauthorisedAccessException extends RuntimeException {
    public UnauthorisedAccessException(String errorMessage) {
        super(errorMessage);
    }
}
