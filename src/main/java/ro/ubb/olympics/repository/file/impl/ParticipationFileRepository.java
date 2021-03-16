package ro.ubb.olympics.repository.file.impl;

import ro.ubb.olympics.domain.Participation;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.file.AbstractFileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Participation file repository.
 */
public class ParticipationFileRepository extends AbstractFileRepository<Long, Participation> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public ParticipationFileRepository(final Validator<Participation> validator, final String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Participation readEntity(final String line) {
        final List<String> tokens = Arrays.asList(line.split(Pattern.quote(SEPARATOR)));

        validateInputTokens(tokens);

        return new Participation(
            Long.parseLong(tokens.get(EntityFieldIndexes.ID.getIndex())),
            Long.parseLong(tokens.get(EntityFieldIndexes.ATHLETE_ID.getIndex())),
            Long.parseLong(tokens.get(EntityFieldIndexes.COMPETITION_ID.getIndex())),
            Integer.parseInt(tokens.get(EntityFieldIndexes.RANK.getIndex()))
        );
    }

    @Override
    protected String writeEntity(final Participation entity) {
        return String.join(
            SEPARATOR,
            entity.getId().toString(),
            entity.getAthleteId().toString(),
            entity.getCompetitionId().toString(),
            String.valueOf(entity.getRank())
        );
    }

    @Override
    protected void validateInputTokens(final List<String> tokens) throws ValidatorException {
        final StringBuilder errorMessageBuilder = new StringBuilder();

        Validator.validateProperty(
            tokens.size(),
            size -> size != EntityFieldIndexes.NUMBER_OF_FIELDS.getIndex(),
            String.format("The input line should contain %d fields.", EntityFieldIndexes.NUMBER_OF_FIELDS.getIndex()),
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while parsing input line for Participation: %s", errorMessage));
            });

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(tokens.get(EntityFieldIndexes.ID.getIndex())),
            "The ID should be a parsable Long value.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(tokens.get(EntityFieldIndexes.ATHLETE_ID.getIndex())),
            "The athleteId should be a parsable Long value.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(tokens.get(EntityFieldIndexes.COMPETITION_ID.getIndex())),
            "The competitionId should be a parsable Long value.",
            errorMessageBuilder
        );


        Validator.validateDoesNotThrowException(
            () -> Integer.parseInt(tokens.get(EntityFieldIndexes.RANK.getIndex())),
            "The rank should be a parsable int value.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while parsing input line for Participation: %s", errorMessage));
            });
    }

    private enum EntityFieldIndexes {

        ID(0), ATHLETE_ID(1), COMPETITION_ID(2), RANK(3), NUMBER_OF_FIELDS(4);

        private final int index;

        EntityFieldIndexes(final int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

    }

}