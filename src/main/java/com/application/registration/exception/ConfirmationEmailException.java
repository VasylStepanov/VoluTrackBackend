package com.application.registration.exception;

public class ConfirmationEmailException extends RuntimeException {

    public ConfirmationEmailException(String text){
        super(text);
    }
}
