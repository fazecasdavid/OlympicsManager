package ro.ubb.olympics.domain.validators;

import ro.ubb.olympics.domain.Sponsor;

import java.util.Optional;

import static java.lang.String.format;


/**
 * This class validates objects of type Sponsor.
 * ~ ID - must be strictly positive
 * ~ name - must be non-empty
 * ~ Country - must be non-empty
 */
public class SponsorValidator implements Validator<Sponsor> {
    @Override
    public void validate(Sponsor entity) throws ValidatorException {
        Validator.validateNonNull(entity);

        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateProperty(
            entity.getId(),
            id -> id <= 0,
            format("Sponsor's ID must be strictly positive, but it is %d.", entity.getId()),
            errorMessageBuilder);

        Validator.validateProperty(
            entity.getName(),
            String::isEmpty,
            "Sponsor's name must be non-empty.",
            errorMessageBuilder);

        Validator.validateProperty(
            entity.getCountry(),
            String::isEmpty,
            "Sponsor's country must be non-empty.",
            errorMessageBuilder);

        Optional.of(errorMessageBuilder.toString())
            .filter(String::isEmpty)
            .orElseThrow(() -> new ValidatorException(
                format("Sponsor #%d: %s", entity.getId(), errorMessageBuilder)));

    }


}
