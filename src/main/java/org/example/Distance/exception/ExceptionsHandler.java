package org.example.Distance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<org.example.Distance.exception.ExceptionDetails> resourceNotFoundException(
            final ResourceNotFoundException exception) {
        org.example.Distance.exception.ExceptionDetails exceptionDetails = new org.example.Distance.exception.ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(org.example.Distance.exception.BadRequestException.class)
    public ResponseEntity<org.example.Distance.exception.ExceptionDetails> badRequestException(
            final org.example.Distance.exception.BadRequestException exception) {
        org.example.Distance.exception.ExceptionDetails exceptionDetails = new org.example.Distance.exception.ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.BAD_REQUEST);
    }
}