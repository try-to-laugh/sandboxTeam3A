package com.sandbox.exception;

public class UsernameAlreadyExistsException extends BudgetRuntimeException {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }

    public UsernameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
