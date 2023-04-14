package com.com3014.userauthservice;

import com.com3014.userauthservice.exceptions.InvalidTokenException;
import com.com3014.userauthservice.exceptions.UserAlreadyExistAuthenticationException;
import com.com3014.userauthservice.exceptions.UserNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

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

    @ExceptionHandler({InvalidTokenException.class, UserNotValidException.class})
    protected ResponseEntity<ApiError> handleBadRequestError(Exception ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, ex);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}