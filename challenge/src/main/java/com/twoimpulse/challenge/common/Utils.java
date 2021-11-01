package com.twoimpulse.challenge.common;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class Utils {

    private final String RFC_5322_EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public UUID convertOrThrowUUID(String uuid){
        try{
            UUID parseUUID = UUID.fromString(uuid);
            return parseUUID;
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid UUID");
        }
    }

    public boolean validateEmail(String emailAddress) {
        return Pattern.compile(RFC_5322_EMAIL_REGEX_PATTERN)
                .matcher(emailAddress)
                .matches();
    }
}
