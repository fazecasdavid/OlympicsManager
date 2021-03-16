package ro.ubb.olympics.repository.file;

import ro.ubb.olympics.domain.BaseEntity;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.exception.FileException;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Abstract file repository.
 *
 * @param <ID> the type of the ID of the stored entities
 * @param <T>  the type of the entity to be stored
 */
public abstract class AbstractFileRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {

    /**
     * Separator used for separating the fields of the entity.
     */
    protected static final String SEPARATOR = "|";

    private final String fileName;

    /**
     * Initializes the repository with the given validator and filename and loads the data from the file.
     *
     * @param validator the validator used to validate the stored entities.
     * @param fileName  the name of the file in which the data is persisted.
     */
    public AbstractFileRepository(final Validator<T> validator, final String fileName) {
        super(validator);
        this.fileName = fileName;
        FileUtils.createFileIfNotExists(fileName);
        loadData();
    }

    /**
     * Validate a list of String tokens representing an entity.
     *
     * @param tokens the tokens representing an entity.
     * @throws ValidatorException if the list of tokens does not correctly represent an entity.
     */
    protected abstract void validateInputTokens(final List<String> tokens) throws ValidatorException;

    /**
     * Read an entity from a line.
     *
     * @param line the String from which the entity is to be read.
     * @return the entity built from the String.
     * @throws ValidatorException if an entity cannot be built from the line.
     */
    protected abstract T readEntity(final String line);

    /**
     * Convert an entity into its string representation to be written to a file.
     *
     * @param entity the entity to be turned into a String representation
     * @return a String representing
     */
    protected abstract String writeEntity(final T entity);

    /**
     * Load the data from a file and save it in the parent InMemoryRepository class.
     */
    private void loadData() {
        try {
            Files
                .lines(Paths.get(fileName))
                .filter(Predicate.not(String::isEmpty))
                .forEach(line -> super.save(readEntity(line)));
        } catch (final IOException ioException) {
            throw new FileException(ioException);
        }
    }

    /**
     * Save the data stored in memory to the file.
     *
     * @throws FileException if an I/O error occurs during the process of saving the data.
     */
    private void saveData() throws FileException {
        try {
            Files.write(
                Paths.get(fileName),
                Collections.singleton(
                    StreamSupport
                        .stream(super.findAll().spliterator(), false)
                        .collect(Collectors.toSet())
                        .stream()
                        .map(this::writeEntity)
                        .collect(Collectors.joining("\n")))
            );
        } catch (final IOException ioException) {
            throw new FileException(ioException);
        }
    }

    @Override
    public Optional<T> save(final T entity) throws ValidatorException {
        final Optional<T> optional = super.save(entity);

        Optional
            .of(optional)
            .filter(Optional::isEmpty)
            .ifPresent(unused -> saveData());

        return optional;
    }

    @Override
    public Optional<T> delete(final ID id) {
        final Optional<T> optional = super.delete(id);

        Optional
            .of(optional)
            .filter(Optional::isPresent)
            .ifPresent(unused -> saveData());

        return optional;
    }

    @Override
    public Optional<T> update(final T entity) throws ValidatorException {
        final Optional<T> optional = super.update(entity);

        Optional
            .of(optional)
            .filter(Optional::isPresent)
            .ifPresent(unused -> saveData());

        return optional;
    }

}