package com.marhasoft.helpdesk.services.exceptions;

public class DataIntegrityViolationException extends RuntimeException {
    private static final long serialVerisonUID = 1L;

    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
