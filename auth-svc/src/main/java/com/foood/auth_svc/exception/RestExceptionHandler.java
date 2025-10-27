package com.foood.auth_svc.exception;

import com.foood.commons_svc.exception.AuthenticationServerException;
import com.foood.commons_svc.exception.EntityNotFoundException;
import com.foood.commons_svc.exception.IllegalStateException;
import com.foood.commons_svc.exception.KeycloakCommunicationException;
import com.foood.commons_svc.exception.InvalidCredentialsException;
import com.foood.commons_svc.util.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorModel apiError = new ErrorModel(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorModel apiError = new ErrorModel(errors, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorModel> handleIllegalStateException(IllegalStateException ex) {
        ErrorModel apiError = new ErrorModel(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ErrorModel apiError = new ErrorModel(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    // 💥 For Keycloak internal failure (500 from Keycloak)
    @ExceptionHandler(AuthenticationServerException.class)
    public ResponseEntity<Object> handleAuthenticationServerException(AuthenticationServerException ex) {
        ErrorModel apiError = new ErrorModel(ex.getMessage(), HttpStatus.BAD_GATEWAY.value());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(apiError);
    }

    // 🚧 For communication or network issues
    @ExceptionHandler(KeycloakCommunicationException.class)
    public ResponseEntity<Object> handleKeycloakCommunicationException(KeycloakCommunicationException ex) {
        ErrorModel apiError = new ErrorModel(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
    }

    // ---------------------------
    // Optional: handle all other exceptions
    // ---------------------------
   /* @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handleGenericException(Exception ex) {
        ErrorModel apiError = new ErrorModel(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }*/
}