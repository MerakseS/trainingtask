package com.qulix.losevsa.trainingtask.web.repository;

/**
 * The exception class for com.qulix.losevsa.trainingtask.web.repository layer.
 */
public class QueryExecutionException extends RuntimeException {

    /**
     * Instantiates a new Repository exception.
     *
     * @param cause the cause
     */
    public QueryExecutionException(Throwable cause) {
        super(cause);
    }
}
