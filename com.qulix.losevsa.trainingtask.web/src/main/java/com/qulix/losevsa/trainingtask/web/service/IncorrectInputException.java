package com.qulix.losevsa.trainingtask.web.service;

/**
 * The exception class for service layer.
 */
public class IncorrectInputException extends RuntimeException {

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    public IncorrectInputException(String message) {
        super(message);
    }
}