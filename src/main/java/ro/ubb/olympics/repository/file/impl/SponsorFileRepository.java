package ro.ubb.olympics.repository.file.impl;

import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.file.AbstractFileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Sponsor file repository.
 */
public class SponsorFileRepository extends AbstractFileRepository<Long, Sponsor> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public SponsorFileRepository(final Validator<Sponsor> validator, final String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Sponsor readEntity(final String line) {
        final List<String> tokens = Arrays.asList(line.split(Pattern.quote(SEPARATOR)));

        validateInputTokens(tokens);

        return new Sponsor(
            Long.parseLong(tokens.get(EntityFieldIndexes.ID.getIndex())),
            tokens.get(EntityFieldIndexes.NAME.getIndex()),
            tokens.get(EntityFieldIndexes.COUNTRY.getIndex())
        );
    }

    @Override
    protected String writeEntity(final Sponsor entity) {
        return String.join(
            SEPARATOR,
            entity.getId().toString(),
            entity.getName(),
            entity.getCountry()
        );
    }

    @Override
    protected void validateInputTokens(final List<String> tokens) throws ValidatorException {
        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateProperty(
            tokens.size(),
            (size) -> size != EntityFieldIndexes.NUMBER_OF_FIELDS.getIndex(),
            String.format("The input line should contain %d fields.", EntityFieldIndexes.NUMBER_OF_FIELDS.getIndex()),
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while parsing input line for Sponsor: %s", errorMessage));
            });

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(tokens.get(EntityFieldIndexes.ID.getIndex())),
            "The ID should be a parsable Long value.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while parsing input line for Sponsor: %s", errorMessage));
            });
    }

    private enum EntityFieldIndexes {

        ID(0), NAME(1), COUNTRY(2), NUMBER_OF_FIELDS(3);

        private final int index;

        EntityFieldIndexes(final int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

    }

}