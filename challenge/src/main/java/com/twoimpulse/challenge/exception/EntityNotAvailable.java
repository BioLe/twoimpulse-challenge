package com.twoimpulse.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EntityNotAvailable extends RuntimeException {

    public EntityNotAvailable(String errorMessage){
        super(errorMessage);
    }

}