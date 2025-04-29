package com.ahusain.journalapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class MethodRequiredException extends RuntimeException{
public MethodRequiredException(String message){
    super(message);
}
}
