package org.example.distance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<org.example.distance.exception.ExceptionDetails> resourceNotFoundException(
            final ResourceNotFoundException exception) {
        org.example.distance.exception.ExceptionDetails exceptionDetails = new org.example.distance.exception.ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(org.example.distance.exception.BadRequestException.class)
    public ResponseEntity<org.example.distance.exception.ExceptionDetails> badRequestException(
            final org.example.distance.exception.BadRequestException exception) {
        org.example.distance.exception.ExceptionDetails exceptionDetails = new org.example.distance.exception.ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.BAD_REQUEST);
    }
}