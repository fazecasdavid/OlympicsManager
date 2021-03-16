package ro.ubb.olympics.repository.file.impl;

import ro.ubb.olympics.domain.Athlete;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.file.AbstractFileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Athlete file repository.
 */
public class AthleteFileRepository extends AbstractFileRepository<Long, Athlete> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public AthleteFileRepository(final Validator<Athlete> validator, final String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Athlete readEntity(final String line) {
        final List<String> tokens = Arrays.asList(line.split(Pattern.quote(SEPARATOR)));

        validateInputTokens(tokens);

        return new Athlete(
            Long.parseLong(tokens.get(AthleteFileRepository.EntityFieldIndexes.ID.getIndex())),
            tokens.get(EntityFieldIndexes.FIRST_NAME.getIndex()),
            tokens.get(EntityFieldIndexes.LAST_NAME.getIndex()),
            tokens.get(EntityFieldIndexes.COUNTRY.getIndex()),
            Integer.parseInt(tokens.get(AthleteFileRepository.EntityFieldIndexes.AGE.getIndex()))
        );
    }

    @Override
    protected String writeEntity(final Athlete entity) {
        return String.join(
            SEPARATOR,
            entity.getId().toString(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getCountry(),
            String.valueOf(entity.getAge())
        );
    }

    @Override
    protected void validateInputTokens(final List<String> tokens) throws ValidatorException {
        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateProperty(
            tokens.size(),
            size -> size != AthleteFileRepository.EntityFieldIndexes.NUMBER_OF_FIELDS.getIndex(),
            String.format("The input line should contain %d fields.", AthleteFileRepository.EntityFieldIndexes.NUMBER_OF_FIELDS.getIndex()),
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while parsing input line for Athlete: %s", errorMessage));
            });

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(tokens.get(AthleteFileRepository.EntityFieldIndexes.ID.getIndex())),
            "The ID should be a parsable Long value.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Integer.parseInt(tokens.get(AthleteFileRepository.EntityFieldIndexes.AGE.getIndex())),
            "The age should be a parsable int value.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while parsing input line for Athlete: %s", errorMessage));
            });
    }

    private enum EntityFieldIndexes {

        ID(0), FIRST_NAME(1), LAST_NAME(2), COUNTRY(3), AGE(4), NUMBER_OF_FIELDS(5);

        private final int index;

        EntityFieldIndexes(final int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

    }

}