package com.vikas.TrackerAPI.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ExpenseNotFoundException ex, WebRequest request) {
        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setStatusMessage(ex.getMessage());
        error.getTimestamp( );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemAlreadyExistException.class)
    public ResponseEntity<ErrorObject> handleItemAlreadyExistException(ItemAlreadyExistException ex, WebRequest request) {
        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setStatusMessage(ex.getMessage());
        error.getTimestamp() ;
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorObject error = new ErrorObject();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setStatusMessage(ex.getMessage());
        error.getTimestamp( );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
