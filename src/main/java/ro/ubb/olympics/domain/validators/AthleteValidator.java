package ro.ubb.olympics.domain.validators;

import ro.ubb.olympics.domain.Athlete;

import java.util.Optional;

/**
 * This class validates objects of type Athlete.
 * ~ ID - must be strictly positive
 * ~ First name - must be non-empty
 * ~ Last name - must be non-empty
 * ~ Country - must be non-empty
 * ~ Age - must be strictly positive
 *
 * @author Sabina.
 */
public class AthleteValidator implements Validator<Athlete> {

    /**
     * Validate an Athlete entity
     *
     * @param entity ~ the entity to be validated
     * @throws ValidatorException if one of the conditions (at least) is not met
     */
    @Override
    public void validate(final Athlete entity) throws ValidatorException {
        Validator.validateNonNull(entity);

        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateProperty(
            entity.getId(),
            id -> id <= 0,
            String.format("Athlete's ID must be strictly positive, but it is %d.", entity.getId()),
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getFirstName(),
            String::isEmpty,
            "Athlete's first name must be non-empty.",
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getLastName(),
            String::isEmpty,
            "Athlete's last name must be non-empty.",
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getCountry(),
            String::isEmpty,
            "Athlete's country must be non-empty.",
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getAge(),
            age -> age <= 0,
            String.format("Athlete's age must be strictly positive, but it is %d.", entity.getAge()),
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Athlete #%d: %s", entity.getId(), errorMessage));
            });
    }

}