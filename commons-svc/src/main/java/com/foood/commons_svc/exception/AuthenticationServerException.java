package com.foood.commons_svc.exception;

public class AuthenticationServerException extends RuntimeException {
    public AuthenticationServerException(String message) {
        super(message);
    }
}
