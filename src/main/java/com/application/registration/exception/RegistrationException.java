package com.application.registration.exception;

import lombok.Getter;

@Getter
public class RegistrationException extends RuntimeException {
    public RegistrationException(String text){
        super(text);
    }
}
