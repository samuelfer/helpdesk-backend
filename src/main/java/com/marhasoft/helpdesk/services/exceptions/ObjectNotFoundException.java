package com.marhasoft.helpdesk.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVerisonUID = 1L;

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
