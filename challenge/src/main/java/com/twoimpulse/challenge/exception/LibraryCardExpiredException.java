package com.twoimpulse.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LibraryCardExpiredException extends RuntimeException {
    public LibraryCardExpiredException(String errorMessage){
        super(errorMessage);
    }
}