package ro.ubb.olympics.repository.jdbc;

import ro.ubb.olympics.domain.BaseEntity;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.domain.validators.ValidatorException;
import ro.ubb.olympics.exception.SqlException;
import ro.ubb.olympics.repository.inmemory.InMemoryRepository;
import ro.ubb.olympics.utils.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * Abstract JDBC repository.
 *
 * @param <ID> the type of the ID of the stored entities
 * @param <T>  the type of the entity to be stored
 */
public abstract class AbstractJdbcRepository<ID, T extends BaseEntity<ID>> extends InMemoryRepository<ID, T> {

    /**
     * The database provider used for database interaction.
     */
    protected final DatabaseProvider databaseProvider;

    /**
     * The name of the table on which the repository operates.
     */
    protected final String tableName;

    /**
     * Initializes the JDBC repository by creating the table if it does not exist and loading the data into the cached
     * InMemoryRepository.
     *
     * @param validator        the validator used to validate the entities stored.
     * @param databaseProvider the database provider used in the repository.
     * @param tableName        the name of the table on which the repository operates.
     */
    public AbstractJdbcRepository(final Validator<T> validator, final DatabaseProvider databaseProvider, final String tableName) {
        super(validator);
        this.databaseProvider = databaseProvider;
        this.tableName = tableName;
        initializeTableIfNotExists();
        loadData();
    }

    /**
     * Create the table used by the repository if it does not exist.
     */
    private void initializeTableIfNotExists() {
        final Pair<Connection, PreparedStatement> connectionAndStatement = getCreateTableIfNotExistsStatement();
        try (
            final Connection ignored = connectionAndStatement.getFirst();
            final PreparedStatement preparedStatement = connectionAndStatement.getSecond()
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new SqlException(sqlException);
        }
    }

    /**
     * Load the data from the database and save it in the parent InMemoryRepository class.
     */
    private void loadData() {
        final String selectAllSql = String.format("SELECT * FROM %s", tableName);
        final Pair<Connection, PreparedStatement> connectionAndStatement = databaseProvider.createPreparedStatement(selectAllSql);
        try (
            final Connection ignored = connectionAndStatement.getFirst();
            final PreparedStatement preparedStatement = connectionAndStatement.getSecond();
            final ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            final AtomicBoolean finishedLoading = new AtomicBoolean(false);
            IntStream.generate(() -> 0)
                .takeWhile(unused -> !finishedLoading.get())
                .forEach(e -> {
                    try {
                        boolean isDone = !resultSet.next();
                        finishedLoading.set(isDone);

                        Optional
                            .of(isDone)
                            .filter(done -> !done)
                            .ifPresent(x -> super.save(loadEntity(resultSet)));

                    } catch (SQLException sqlException) {
                        throw new SqlException(sqlException);
                    }
                });
        } catch (SQLException sqlException) {
            throw new SqlException(sqlException);
        }
    }

    /**
     * Load and build an entity from the result set.
     *
     * @param resultSet the result set used to build the entity
     * @return the entity built from the result set.
     */
    protected abstract T loadEntity(final ResultSet resultSet);

    /**
     * Build a PreparedStatement representing the SQL CREATE TABLE command used to create the table if it does not exist.
     *
     * @return a pair containing a PreparedStatement and its associated connection representing the SQL CREATE TABLE command.
     */
    protected abstract Pair<Connection, PreparedStatement> getCreateTableIfNotExistsStatement();

    /**
     * Build a PreparedStatement representing the SQL INSERT command for a given entity.
     *
     * @param entity the entity to be inserted
     * @return a pair containing a PreparedStatement and its associated connection representing the SQL INSERT command.
     */
    protected abstract Pair<Connection, PreparedStatement> getInsertStatement(final T entity);

    /**
     * Build a PreparedStatement representing the SQL DELETE command for a given entity ID.
     *
     * @param id the id of the entity to be deleted
     * @return a pair containing a PreparedStatement and its associated connection representing the SQL DELETE command.
     */
    protected abstract Pair<Connection, PreparedStatement> getDeleteStatement(final ID id);

    /**
     * Build a PreparedStatement representing the SQL UPDATE command for a given entity.
     *
     * @param entity the entity to be updated
     * @return a pair containing a PreparedStatement and its associated connection representing the SQL UPDATE command.
     */
    protected abstract Pair<Connection, PreparedStatement> getUpdateStatement(final T entity);

    @Override
    public Optional<T> save(final T entity) throws ValidatorException {
        final Optional<T> optional = super.save(entity);

        Optional
            .of(optional)
            .filter(Optional::isEmpty)
            .ifPresent(unused -> {
                final Pair<Connection, PreparedStatement> connectionAndStatement = getInsertStatement(entity);
                try (
                    final Connection ignored = connectionAndStatement.getFirst();
                    final PreparedStatement preparedStatement = connectionAndStatement.getSecond()
                ) {
                    preparedStatement.executeUpdate();
                } catch (SQLException sqlException) {
                    throw new SqlException(sqlException);
                }
            });

        return optional;
    }

    @Override
    public Optional<T> delete(final ID id) {
        final Optional<T> optional = super.delete(id);

        Optional
            .of(optional)
            .filter(Optional::isPresent)
            .ifPresent(unused -> {
                final Pair<Connection, PreparedStatement> connectionAndStatement = getDeleteStatement(id);
                try (
                    final Connection ignored = connectionAndStatement.getFirst();
                    final PreparedStatement preparedStatement = connectionAndStatement.getSecond()
                ) {
                    preparedStatement.executeUpdate();
                } catch (SQLException sqlException) {
                    throw new SqlException(sqlException);
                }
            });

        return optional;
    }

    @Override
    public Optional<T> update(final T entity) throws ValidatorException {
        final Optional<T> optional = super.update(entity);

        Optional
            .of(optional)
            .filter(Optional::isPresent)
            .ifPresent(unused -> {
                final Pair<Connection, PreparedStatement> connectionAndStatement = getUpdateStatement(entity);
                try (
                    final Connection ignored = connectionAndStatement.getFirst();
                    final PreparedStatement preparedStatement = connectionAndStatement.getSecond()
                ) {
                    preparedStatement.executeUpdate();
                } catch (SQLException sqlException) {
                    throw new SqlException(sqlException);
                }
            });

        return optional;
    }

}