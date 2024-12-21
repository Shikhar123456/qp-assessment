package com.example.grocery.Exception;

import com.example.grocery.factory.ApplicationFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInputException(final InvalidInputException e) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return handleErrors(e.getMessage(), status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(final IllegalArgumentException e) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return handleErrors(e.getMessage(), status);
    }

    public ResponseEntity<ExceptionResponse> handleErrors(String errorMessage, HttpStatus status) {
        return new ResponseEntity<>(ApplicationFactory.getInstance().createExceptionResponse(errorMessage, status),
                status);
    }


}
