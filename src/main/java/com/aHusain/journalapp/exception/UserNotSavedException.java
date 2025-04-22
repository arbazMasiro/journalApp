package com.ahusain.journalapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotSavedException extends RuntimeException {
    public UserNotSavedException(String message) {
        super(message);
    }
}
