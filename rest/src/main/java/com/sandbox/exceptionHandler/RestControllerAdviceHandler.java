package com.sandbox.exceptionHandler;

import com.sandbox.exception.BudgetRuntimeException;
import com.sandbox.exception.ResourceNotFoundException;
import com.sandbox.exception.WalletNotFoundException;
import com.sandbox.exception.WalletWithSameNameAndCurrencyExist;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RestControllerAdviceHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestControllerAdviceHandler.class);

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<Object> handleException(Throwable ex) {
        log.error("Exception: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BudgetRuntimeException.class})
    public ResponseEntity<Object> budgetRuntimeException(
            BudgetRuntimeException ex, WebRequest request) {
        log.info("Exception: {}, {}", HttpStatus.BAD_REQUEST, ex.getCause(), ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> resourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        log.info("Exception: {}, {}", HttpStatus.NOT_FOUND, ex.getCause(), ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {WalletNotFoundException.class})
    public ResponseEntity<Object> walletNotFoundException(
            WalletNotFoundException ex, WebRequest request) {
        log.info("Exception: {}, {}", HttpStatus.NOT_FOUND, ex.getCause(), ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {WalletWithSameNameAndCurrencyExist.class})
    public ResponseEntity<Object> walletWithSameNameAndCurrencyExist(
            WalletWithSameNameAndCurrencyExist ex, WebRequest request) {
        log.info("Exception: {}, {}", HttpStatus.BAD_REQUEST, ex.getCause(), ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
