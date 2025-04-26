package com.vikas.TrackerAPI.Exception;

import org.springframework.http.HttpStatus;

public class ExpenseNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
