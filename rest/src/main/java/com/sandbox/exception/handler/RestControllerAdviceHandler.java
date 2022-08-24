package com.sandbox.exception.handler;

import com.sandbox.exception.BudgetRuntimeException;
import com.sandbox.exception.ResourceNotFoundException;
import com.sandbox.exception.UsernameAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerAdviceHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestControllerAdviceHandler.class);

    private static final String LOGGER_SERVER_EXCEPTION = "Server exception: {}";
    private static final String LOGGER_BAD_REQUEST_EXCEPTION = "Bad request exception: {}";
    private static final String LOGGER_RESOURCE_NOT_FOUND_EXCEPTION = "Resource not found exception: {}";
    private static final String LOGGER_UNAUTHORIZED_EXCEPTION = "Unauthorized exception: {}";
    private static final String LOGGER_USERNAME_ALREADY_EXISTS_EXCEPTION = "Username already exists exception: {}";


    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<Object> handleException(Throwable ex) {
        LOG.error(LOGGER_SERVER_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BudgetRuntimeException.class})
    public ResponseEntity<Object> budgetRuntimeException(
            BudgetRuntimeException ex, WebRequest request) {
        LOG.info(LOGGER_BAD_REQUEST_EXCEPTION, HttpStatus.BAD_REQUEST, ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> resourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        LOG.info(LOGGER_RESOURCE_NOT_FOUND_EXCEPTION, HttpStatus.NOT_FOUND, ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> unauthorizedException(
            AuthenticationException ex, WebRequest request) {
        LOG.info(LOGGER_UNAUTHORIZED_EXCEPTION, HttpStatus.UNAUTHORIZED, ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> usernameAlreadyExistsException(
            UsernameAlreadyExistsException ex, WebRequest request) {
        LOG.info(LOGGER_USERNAME_ALREADY_EXISTS_EXCEPTION, HttpStatus.CONFLICT, ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
