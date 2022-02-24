package com.qulix.losevsa.trainingtask.web.database;

/**
 * The type No connection exception.
 */
public class NoConnectionException extends RuntimeException {

    /**
     * Instantiates a new No connection exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public NoConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
