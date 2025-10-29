package com.foood.user.exception;

import com.foood.commons_svc.util.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException.Conflict ex){
        ErrorModel errorModel = new ErrorModel(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorModel);
    }
}
