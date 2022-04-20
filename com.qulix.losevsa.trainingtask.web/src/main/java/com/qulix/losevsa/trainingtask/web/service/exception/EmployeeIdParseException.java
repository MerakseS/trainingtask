package com.qulix.losevsa.trainingtask.web.service.exception;

public class EmployeeIdParseException extends RuntimeException {

    /**
     * Instantiates a new Date parse exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public EmployeeIdParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
