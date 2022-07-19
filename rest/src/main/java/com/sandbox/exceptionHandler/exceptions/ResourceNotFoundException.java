package com.sandbox.exceptionHandler.exceptions;

public class ResourceNotFoundException extends BudgetRuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
