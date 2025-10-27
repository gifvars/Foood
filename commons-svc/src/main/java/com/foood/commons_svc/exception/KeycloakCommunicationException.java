package com.foood.commons_svc.exception;

public class KeycloakCommunicationException extends RuntimeException {
    public KeycloakCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
