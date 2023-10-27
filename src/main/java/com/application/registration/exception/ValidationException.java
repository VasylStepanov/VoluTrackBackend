package com.application.registration.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    public ValidationException(String text){
        super(text);
    }
}