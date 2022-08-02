package com.sandbox.exception;

public class UnauthorizedException extends BudgetRuntimeException{

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
