package ro.ubb.olympics.repository.file.impl;

import lombok.SneakyThrows;
import ro.ubb.olympics.domain.Competition;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.file.AbstractFileRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Competition file repository.
 */
public class CompetitionFileRepository extends AbstractFileRepository<Long, Competition> {

    /**
     * Initializes the repository with the given validator and filename.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public CompetitionFileRepository(final Validator<Competition> validator, final String fileName) {
        super(validator, fileName);
    }

    @SneakyThrows
    @Override
    protected Competition readEntity(final String line) {
        final List<String> tokens = Arrays.asList(line.split(Pattern.quote(SEPARATOR)));

        validateInputTokens(tokens);

        return new Competition(
            Long.parseLong(tokens.get(EntityFieldIndexes.ID.getIndex())),
            new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(tokens.get(CompetitionFileRepository.EntityFieldIndexes.DATE.getIndex())),
            tokens.get(EntityFieldIndexes.LOCATION.getIndex()),
            tokens.get(EntityFieldIndexes.NAME.getIndex()),
            tokens.get(EntityFieldIndexes.DESCRIPTION.getIndex())
        );
    }

    @Override
    protected String writeEntity(final Competition entity) {
        return String.join(
            SEPARATOR,
            entity.getId().toString(),
            new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(entity.getDate()),
            entity.getLocation(),
            entity.getName(),
            entity.getDescription()
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
                throw new ValidatorException(String.format("Error while parsing input line for Competition: %s", errorMessage));
            });

        Validator.validateDoesNotThrowException(
            () -> Long.parseLong(tokens.get(EntityFieldIndexes.ID.getIndex())),
            "The ID should be a parsable Long value.",
            errorMessageBuilder
        );

        Validator.validateDoesNotThrowException(
            () -> {
                try {
                    new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(tokens.get(EntityFieldIndexes.DATE.getIndex()));
                } catch (final ParseException e) {
                    throw new RuntimeException();
                }
            },
            "The Date should be in the format dd-MM-yyyy.",
            errorMessageBuilder
        );

        Optional
            .of(errorMessageBuilder.toString())
            .filter(errorMessage -> !errorMessage.isEmpty())
            .ifPresent(errorMessage -> {
                throw new ValidatorException(String.format("Error while parsing input line for Competition: %s", errorMessage));
            });
    }

    private enum EntityFieldIndexes {

        ID(0), DATE(1), LOCATION(2), NAME(3), DESCRIPTION(4), NUMBER_OF_FIELDS(5);

        private final int index;

        EntityFieldIndexes(final int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

    }

}