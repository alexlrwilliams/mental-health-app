package com.com3014.userauthservice;

import com.com3014.userauthservice.exceptions.UserAlreadyExistAuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserAlreadyExistAuthenticationException.class})
    protected ResponseEntity<ApiError> handleConflictErrors(Exception ex) {
        ApiError apiError = new ApiError(CONFLICT, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    protected ResponseEntity<ApiError> handleNotFoundError(Exception ex) {
        ApiError apiError = new ApiError(NOT_FOUND, ex);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}