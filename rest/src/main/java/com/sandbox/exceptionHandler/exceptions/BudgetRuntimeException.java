package com.sandbox.exceptionHandler.exceptions;

public class BudgetRuntimeException extends RuntimeException {

    public BudgetRuntimeException(String message) {
        super(message);
    }

    public BudgetRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
