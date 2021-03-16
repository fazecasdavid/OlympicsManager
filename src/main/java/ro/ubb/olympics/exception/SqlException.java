package ro.ubb.olympics.exception;

/**
 * Exception used in case of SQL or database unexpected events.
 */
public class SqlException extends RuntimeException {

    /**
     * Initializes the exception with the given cause.
     *
     * @param cause the cause to be passed to the parent exception.
     */
    public SqlException(final Throwable cause) {
        super(cause);
    }

}