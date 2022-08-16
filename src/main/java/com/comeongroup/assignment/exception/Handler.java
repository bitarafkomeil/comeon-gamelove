package com.comeongroup.assignment.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDateTime(LocalDateTime.now());
        exceptionResponse.setMessage(ex.getMessage());
        HttpStatus status = ex instanceof ElementNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(exceptionResponse, status);
    }
}