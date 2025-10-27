package com.foood.commons_svc.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
