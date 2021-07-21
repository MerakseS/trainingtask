package repository;

/**
 * The exception class for repository layer.
 */
public class RepositoryException extends RuntimeException {
    /**
     * Instantiates a new Repository exception.
     */
    public RepositoryException() {
    }

    /**
     * Instantiates a new Repository exception.
     *
     * @param message the message
     */
    public RepositoryException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Repository exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Repository exception.
     *
     * @param cause the cause
     */
    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
