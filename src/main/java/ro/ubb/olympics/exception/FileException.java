package ro.ubb.olympics.exception;

/**
 * Exception used in case of I/O unexpected events.
 */
public class FileException extends RuntimeException {

    /**
     * Initializes the exception with the given cause.
     *
     * @param cause the cause to be passed to the parent exception.
     */
    public FileException(final Throwable cause) {
        super(cause);
    }

}