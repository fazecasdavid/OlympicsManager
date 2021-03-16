package ro.ubb.olympics.domain.validators;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Generic validator interface.
 *
 * @param <T> type of entity to be validated
 */
public interface Validator<T> {

    /**
     * Separator for building sequences of error messages when validating an entity.
     */
    String EXCEPTION_MESSAGE_SEPARATOR = " ";

    /**
     * Validates a property given a predicate. If the property is invalid, an error message is appended to an error message builder.
     *
     * @param property                 the property to be validated
     * @param predicate                the predicate for which the property will be checked against
     * @param invalidFieldErrorMessage the message to be added to the error message builder
     * @param errorMessageBuilder      the error message builder to which error messages are appended
     * @param <T>                      generic type parameter
     */
    static <T> void validateProperty(final T property, final Predicate<T> predicate, final String invalidFieldErrorMessage, final StringBuilder errorMessageBuilder) {
        Optional
            .of(property)
            .filter(predicate)
            .ifPresent(unused -> {
                errorMessageBuilder.append(invalidFieldErrorMessage);
                errorMessageBuilder.append(Validator.EXCEPTION_MESSAGE_SEPARATOR);
            });
    }

    /**
     * Checks whether an object is null. If it is, throws an exception with a given message.
     *
     * @param object           the object to be checked if it is null
     * @param exceptionMessage the exception message
     * @param <T>              generic type parameter
     * @throws IllegalArgumentException if the object is null
     */
    static <T> void validateNonNull(final T object, final String exceptionMessage) throws IllegalArgumentException {
        Optional
            .ofNullable(object)
            .ifPresentOrElse(
                unused -> {
                },
                () -> {
                    throw new IllegalArgumentException(exceptionMessage);
                }
            );
    }

    /**
     * Checks whether an object is null. If it is, throws an exception with a predefined message.
     *
     * @param object the object to be checked if it is null
     * @param <T>    generic type parameter
     * @throws ValidatorException if the object is null
     */
    static <T> void validateNonNull(final T object) throws ValidatorException {
        validateNonNull(object, "The object must be non-null.");
    }

    /**
     * Check whether a method call throws an exception. If it does, an error message is appended to an error message builder.
     *
     * @param runnable            the method call to be made.
     * @param errorMessage        the message to be added to the error message builder
     * @param errorMessageBuilder the error message builder to which error messages are appended
     */
    static void validateDoesNotThrowException(final Runnable runnable, final String errorMessage, final StringBuilder errorMessageBuilder) {
        try {
            runnable.run();
        } catch (final Exception exception) {
            errorMessageBuilder.append(errorMessage);
            errorMessageBuilder.append(EXCEPTION_MESSAGE_SEPARATOR);
        }
    }

    /**
     * Validates an entity.
     *
     * @param entity the entity to be validated
     * @throws ValidatorException if the entity is not valid
     */
    void validate(final T entity) throws ValidatorException;

}