package controller;

/**
 * The exception class for controller layer.
 */
public class ControllerException extends RuntimeException {
    /**
     * Instantiates a new Controller exception.
     */
    public ControllerException() {
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param message the message
     */
    public ControllerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Controller exception.
     *
     * @param cause the cause
     */
    public ControllerException(Throwable cause) {
        super(cause);
    }
}
