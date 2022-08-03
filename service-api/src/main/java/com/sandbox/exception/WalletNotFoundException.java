package com.sandbox.exceptionHandler.exceptions;

public class WalletNotFoundException extends ResourceNotFoundException {
    public WalletNotFoundException(String message) {
        super(message);
    }

    public WalletNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
