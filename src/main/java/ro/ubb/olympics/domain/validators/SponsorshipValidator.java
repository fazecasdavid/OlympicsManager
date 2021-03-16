package ro.ubb.olympics.domain.validators;

import ro.ubb.olympics.domain.Sponsorship;

import java.util.Optional;

/**
 * This class validates objects of type Sponsorship.
 * ~ ID - must be strictly positive
 * ~ moneyContribution - must be strictly positive
 */
public class SponsorshipValidator implements Validator<Sponsorship> {

    /**
     * Validate a Sponsorship entity
     *
     * @param entity ~ the entity to be validated
     * @throws ValidatorException if at least one condition is not met
     */
    @Override
    public void validate(final Sponsorship entity) throws ValidatorException {
        Validator.validateNonNull(entity);

        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateProperty(
            entity.getId(),
            id -> id <= 0,
            String.format("Sponsorship's ID must be strictly positive, but it is %d.", entity.getId()),
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getSponsorId(),
            id -> id <= 0,
            String.format("Sponsorship's sponsorId must be strictly positive, but it is %d.", entity.getSponsorId()),
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getCompetitionId(),
            id -> id <= 0,
            String.format("Sponsorship's competitionId must be strictly positive, but it is %d.", entity.getCompetitionId()),
            errorMessageBuilder
        );

        Validator.validateProperty(
            entity.getMoneyContribution(),
            moneyContribution -> moneyContribution <= 0,
            String.format("Sponsorship's money contribution must be strictly positive, but it is %d.", entity.getMoneyContribution()),
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Sponsorship #%d: %s", entity.getId(), errorMessage));
            });
    }

}