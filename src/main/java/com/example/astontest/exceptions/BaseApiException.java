package com.example.astontest.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BaseApiException extends Exception {
    @Getter
    private final HttpStatus status;
    public BaseApiException(String message){
        this(HttpStatus.BAD_REQUEST, message);
    }
    public BaseApiException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
