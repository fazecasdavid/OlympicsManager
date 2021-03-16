package ro.ubb.olympics.repository.jdbc.impl;

import ro.ubb.olympics.domain.Sponsor;
import ro.ubb.olympics.domain.validators.Validator;
import ro.ubb.olympics.exception.SqlException;
import ro.ubb.olympics.repository.jdbc.AbstractJdbcRepository;
import ro.ubb.olympics.repository.jdbc.DatabaseProvider;
import ro.ubb.olympics.utils.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Sponsor JDBC repository.
 */
public class SponsorJdbcRepository extends AbstractJdbcRepository<Long, Sponsor> {

    /**
     * Initializes the repository with the given validator, database provider and table name.
     *
     * @param validator        the validator used to validate the stored entities.
     * @param databaseProvider the database provider used by the repository.
     * @param tableName        the name of the table on which the repository operates.
     */
    public SponsorJdbcRepository(final Validator<Sponsor> validator, final DatabaseProvider databaseProvider, final String tableName) {
        super(validator, databaseProvider, tableName);
    }

    @Override
    protected Sponsor loadEntity(final ResultSet resultSet) {
        try {
            return new Sponsor(
                resultSet.getLong(EntityFieldColumnNames.ID.getColumnName()),
                resultSet.getString(EntityFieldColumnNames.NAME.getColumnName()),
                resultSet.getString(EntityFieldColumnNames.COUNTRY.getColumnName())
            );
        } catch (SQLException exception) {
            throw new SqlException(exception);
        }
    }

    @Override
    protected Pair<Connection, PreparedStatement> getCreateTableIfNotExistsStatement() {
        final String createTableIfNotExistsSql = String.format(
            "CREATE TABLE IF NOT EXISTS %s (" +
                "   %s %s," +
                "   %s %s," +
                "   %s %s" +
                ");",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(), EntityFieldColumnNames.ID.getProperties(),
            EntityFieldColumnNames.NAME.getColumnName(), EntityFieldColumnNames.NAME.getProperties(),
            EntityFieldColumnNames.COUNTRY.getColumnName(), EntityFieldColumnNames.COUNTRY.getProperties()
        );

        return databaseProvider.createPreparedStatement(createTableIfNotExistsSql);
    }

    @Override
    protected Pair<Connection, PreparedStatement> getInsertStatement(final Sponsor entity) {
        final String insertEntitySql = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(),
            EntityFieldColumnNames.NAME.getColumnName(),
            EntityFieldColumnNames.COUNTRY.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            insertEntitySql,
            entity.getId(),
            entity.getName(),
            entity.getCountry()
        );
    }

    @Override
    protected Pair<Connection, PreparedStatement> getDeleteStatement(final Long id) {
        final String deleteEntitySql = String.format(
            "DELETE FROM %s WHERE %s = ?",
            tableName,
            EntityFieldColumnNames.ID.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            deleteEntitySql,
            id
        );
    }

    @Override
    protected Pair<Connection, PreparedStatement> getUpdateStatement(final Sponsor entity) {
        final String updateEntitySql = String.format(
            "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
            tableName,
            EntityFieldColumnNames.NAME.getColumnName(),
            EntityFieldColumnNames.COUNTRY.getColumnName(),
            EntityFieldColumnNames.ID.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            updateEntitySql,
            entity.getName(),
            entity.getCountry(),
            entity.getId()
        );
    }

    private enum EntityFieldColumnNames {

        ID("id", "INT PRIMARY KEY NOT NULL"),
        NAME("name", "VARCHAR(255) NOT NULL"),
        COUNTRY("country", "VARCHAR(255) NOT NULL");

        private final String columnName;
        private final String properties;

        EntityFieldColumnNames(final String columnName, final String properties) {
            this.columnName = columnName;
            this.properties = properties;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getProperties() {
            return properties;
        }

    }

}