package com.qulix.losevsa.trainingtask.web.repository;

/**
 * The exception class for repository layer.
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

    /**
     * Instantiates a new Repository exception.
     *
     * @param cause the cause
     */
    public QueryExecutionException(Throwable cause) {
        super(cause);
    }
}
