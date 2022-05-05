package com.qulix.losevsa.trainingtask.web.repository;

/**
 * An exception that provides information on a database access error or other errors.
 */
public class QueryExecutionException extends RuntimeException {

    /**
     * Instantiates a new Query execution exception.
     *
     * @param message the message
     */
    public QueryExecutionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Query execution exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public QueryExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

}
