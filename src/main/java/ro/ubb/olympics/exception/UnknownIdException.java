package ro.ubb.olympics.exception;

/**
 * Exception used in case an entity with an unknown ID is required.
 */
public class UnknownIdException extends RuntimeException {

    /**
     * Initializes the exception with the given message cause.
     *
     * @param message the message cause to be passed to the parent exception.
     */
    public UnknownIdException(final String message) {
        super(message);
    }

}