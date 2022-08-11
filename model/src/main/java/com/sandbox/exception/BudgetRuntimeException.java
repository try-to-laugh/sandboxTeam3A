package com.sandbox.exception;

public class BudgetRuntimeException extends RuntimeException {

    public BudgetRuntimeException(String message) {
        super(message);
    }

    public BudgetRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
