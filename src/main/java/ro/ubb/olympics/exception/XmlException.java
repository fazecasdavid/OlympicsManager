package ro.ubb.olympics.exception;

/**
 * Exception used in case of I/O or XML unexpected events.
 */
public class XmlException extends RuntimeException {

    /**
     * Initializes the exception with the given cause.
     *
     * @param cause the cause to be passed to the parent exception.
     */
    public XmlException(final Throwable cause) {
        super(cause);
    }

}