package ro.ubb.olympics.repository.inmemory;

import ro.ubb.olympics.domain.BaseEntity;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.repository.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository for generic CRUD operations on a repository for a specific type.
 *
 * @author radu.
 */
public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    private final Map<ID, T> entities;
    private final Validator<T> validator;

    /**
     * Initializes the repository with the given entity validator and an empty collection of entities.
     *
     * @param validator the validator used to validate entities
     */
    public InMemoryRepository(final Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(final ID id) {
        Validator.validateNonNull(id);
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(entities.values());
    }

    @Override
    public Optional<T> save(final T entity) throws ValidatorException {
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(final ID id) {
        Validator.validateNonNull(id);
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(final T entity) throws ValidatorException {
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }

}