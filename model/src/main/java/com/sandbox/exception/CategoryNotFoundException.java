package com.sandbox.exception;

public class CategoryNotFoundException extends BudgetRuntimeException{

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
