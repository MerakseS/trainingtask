package com.qulix.losevsa.trainingtask.web.service;

/**
 * The exception class for com.qulix.losevsa.trainingtask.web.service layer.
 */
public class IncorrectInputException extends RuntimeException {

    /**
     * Instantiates a new Service exception.
     */
    public IncorrectInputException() {
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    public IncorrectInputException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public IncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param cause the cause
     */
    public IncorrectInputException(Throwable cause) {
        super(cause);
    }
}
