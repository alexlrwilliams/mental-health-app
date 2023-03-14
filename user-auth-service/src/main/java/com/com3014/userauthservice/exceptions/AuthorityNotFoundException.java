package com.com3014.userauthservice.exceptions;

public class AuthorityNotFoundException extends RuntimeException {
    public AuthorityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
