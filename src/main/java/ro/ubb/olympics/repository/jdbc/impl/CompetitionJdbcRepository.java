package ro.ubb.olympics.repository.jdbc.impl;

import ro.ubb.olympics.domain.Competition;
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
 * Competition JDBC repository.
 */
public class CompetitionJdbcRepository extends AbstractJdbcRepository<Long, Competition> {

    /**
     * Initializes the repository with the given validator, database provider and table name.
     *
     * @param validator        the validator used to validate the stored entities.
     * @param databaseProvider the database provider used by the repository.
     * @param tableName        the name of the table on which the repository operates.
     */
    public CompetitionJdbcRepository(final Validator<Competition> validator, final DatabaseProvider databaseProvider, final String tableName) {
        super(validator, databaseProvider, tableName);
    }

    @Override
    protected Competition loadEntity(final ResultSet resultSet) {
        try {
            return new Competition(
                resultSet.getLong(EntityFieldColumnNames.ID.getColumnName()),
                new java.util.Date(resultSet.getDate(EntityFieldColumnNames.DATE.getColumnName()).getTime()),
                resultSet.getString(EntityFieldColumnNames.LOCATION.getColumnName()),
                resultSet.getString(EntityFieldColumnNames.NAME.getColumnName()),
                resultSet.getString(EntityFieldColumnNames.DESCRIPTION.getColumnName())
            );
        } catch (SQLException sqlException) {
            throw new SqlException(sqlException);
        }
    }

    @Override
    protected Pair<Connection, PreparedStatement> getCreateTableIfNotExistsStatement() {
        final String createTableIfNotExistsSql = String.format(
            "CREATE TABLE IF NOT EXISTS %s (" +
                "   %s %s," +
                "   %s %s," +
                "   %s %s," +
                "   %s %s," +
                "   %s %s" +
                ");",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(), EntityFieldColumnNames.ID.getProperties(),
            EntityFieldColumnNames.DATE.getColumnName(), EntityFieldColumnNames.DATE.getProperties(),
            EntityFieldColumnNames.LOCATION.getColumnName(), EntityFieldColumnNames.LOCATION.getProperties(),
            EntityFieldColumnNames.NAME.getColumnName(), EntityFieldColumnNames.NAME.getProperties(),
            EntityFieldColumnNames.DESCRIPTION.getColumnName(), EntityFieldColumnNames.DESCRIPTION.getProperties()
        );

        return databaseProvider.createPreparedStatement(
            createTableIfNotExistsSql
        );
    }

    @Override
    public Pair<Connection, PreparedStatement> getInsertStatement(final Competition entity) {
        final String insertEntitySql = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(),
            EntityFieldColumnNames.DATE.getColumnName(),
            EntityFieldColumnNames.LOCATION.getColumnName(),
            EntityFieldColumnNames.NAME.getColumnName(),
            EntityFieldColumnNames.DESCRIPTION.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            insertEntitySql,
            entity.getId(),
            new java.sql.Date(entity.getDate().getTime()),
            entity.getLocation(),
            entity.getName(),
            entity.getDescription()
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
    protected Pair<Connection, PreparedStatement> getUpdateStatement(final Competition entity) {
        final String updateEntitySql = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
            tableName,
            EntityFieldColumnNames.DATE.getColumnName(),
            EntityFieldColumnNames.LOCATION.getColumnName(),
            EntityFieldColumnNames.NAME.getColumnName(),
            EntityFieldColumnNames.DESCRIPTION.getColumnName(),
            EntityFieldColumnNames.ID.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            updateEntitySql,
            new java.sql.Date(entity.getDate().getTime()),
            entity.getLocation(),
            entity.getName(),
            entity.getDescription(),
            entity.getId()
        );
    }

    private enum EntityFieldColumnNames {

        ID("id", "INT PRIMARY KEY NOT NULL"),
        DATE("competitionDate", "DATE NOT NULL"),
        LOCATION("location", "VARCHAR(255) NOT NULL"),
        NAME("name", "VARCHAR(255) NOT NULL"),
        DESCRIPTION("description", "VARCHAR(255) NOT NULL");

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