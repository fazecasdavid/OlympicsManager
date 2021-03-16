package ro.ubb.olympics.domain.validators;

/**
 * Custom exception raised when an object is found to be invalid during validation.
 */
public class ValidatorException extends RuntimeException {

    /**
     * Initializes the parent class with the provided exception message.
     *
     * @param message the message of the exception
     */
    public ValidatorException(final String message) {
        super(message);
    }

}