package ro.ubb.olympics.domain.validators;

import ro.ubb.olympics.domain.Competition;

import java.util.Optional;

/**
 * This class validates objects of type Competition.
 * ~ ID - must be strictly positive
 * ~ Name - must be non-empty
 * ~ Description - must be non-empty
 * ~ Location - must be non-empty
 *
 * @author David.
 */
public class CompetitionValidator implements Validator<Competition> {

    /**
     * Validate a Competition entity
     *
     * @param entity the competition to be validated
     * @throws ValidatorException if one of the conditions (at least) is not met
     */
    @Override
    public void validate(final Competition entity) throws ValidatorException {
        Validator.validateNonNull(entity);

        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateProperty(
            entity.getId(),
            id -> id <= 0,
            String.format("Competition's ID must be strictly positive, but it is %d.", entity.getId()),
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getName(),
            String::isEmpty,
            "Competition's name must be non-empty.",
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getDescription(),
            String::isEmpty,
            "Competition's description must be non-empty.",
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getLocation(),
            String::isEmpty,
            "Competition's location must be non-empty.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Competition #%d: %s", entity.getId(), errorMessage));
            });
    }

}