package com.com3014.s3service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Bucket Exists")
public class BucketDoesNotExistException extends RuntimeException {
    public BucketDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
