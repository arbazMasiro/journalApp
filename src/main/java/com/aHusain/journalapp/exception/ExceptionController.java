package com.ahusain.journalapp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotSavedException.class)
    public ResponseEntity<String> handleUserNotSavedException(UserNotSavedException userNotSavedException) {
        return new ResponseEntity<>(userNotSavedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}

