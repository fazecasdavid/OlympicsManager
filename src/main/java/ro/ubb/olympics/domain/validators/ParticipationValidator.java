package ro.ubb.olympics.domain.validators;

import ro.ubb.olympics.domain.Participation;

import java.util.Optional;

/**
 * This class validates objects of type Participation.
 * ~ ID - must be strictly positive
 * ~ Rank - must be strictly positive
 * ~ athleteId - must be strictly positive
 * ~ competitionId - must be strictly positive
 *
 * @author Rares.
 */
public class ParticipationValidator implements Validator<Participation> {

    /**
     * Validate a Participation entity
     *
     * @param entity ~ the entity to be validated
     * @throws ValidatorException if at least one condition is not met
     */
    @Override
    public void validate(final Participation entity) throws ValidatorException {
        Validator.validateNonNull(entity);

        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateProperty(
            entity.getId(),
            id -> id <= 0,
            String.format("Participation's ID must be strictly positive, but it is %d.", entity.getId()),
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getAthleteId(),
            id -> id <= 0,
            String.format("Participation's athleteId must be strictly positive, but it is %d.", entity.getAthleteId()),
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getCompetitionId(),
            id -> id <= 0,
            String.format("Participation's competitionId must be strictly positive, but it is %d.", entity.getCompetitionId()),
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getRank(),
            rank -> rank <= 0,
            String.format("Participation's rank must be strictly positive, but it is %d.", entity.getRank()),
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Participation #%d: %s", entity.getId(), errorMessage));
            });
    }

}