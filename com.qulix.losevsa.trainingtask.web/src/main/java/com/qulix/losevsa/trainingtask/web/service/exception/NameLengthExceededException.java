package com.qulix.losevsa.trainingtask.web.service.exception;

/**
 * The type Name length exceeded exception.
 */
public class NameLengthExceededException extends RuntimeException {

    /**
     * Instantiates a new Name length exceeded exception.
     *
     * @param message the message
     */
    public NameLengthExceededException(String message) {
        super(message);
    }
}
