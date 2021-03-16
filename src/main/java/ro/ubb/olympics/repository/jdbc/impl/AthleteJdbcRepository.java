package ro.ubb.olympics.repository.jdbc.impl;

import ro.ubb.olympics.domain.Athlete;
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
 * Athlete JDBC repository.
 */
public class AthleteJdbcRepository extends AbstractJdbcRepository<Long, Athlete> {

    /**
     * Initializes the repository with the given validator, database provider and table name.
     *
     * @param validator        the validator used to validate the stored entities.
     * @param databaseProvider the database provider used by the repository.
     * @param tableName        the name of the table on which the repository operates.
     */
    public AthleteJdbcRepository(final Validator<Athlete> validator, final DatabaseProvider databaseProvider, final String tableName) {
        super(validator, databaseProvider, tableName);
    }

    @Override
    protected Athlete loadEntity(final ResultSet resultSet) {
        try {
            return new Athlete(
                resultSet.getLong(EntityFieldColumnNames.ID.getColumnIndex()),
                resultSet.getString(EntityFieldColumnNames.FIRST_NAME.getColumnIndex()),
                resultSet.getString(EntityFieldColumnNames.LAST_NAME.getColumnIndex()),
                resultSet.getString(EntityFieldColumnNames.COUNTRY.getColumnIndex()),
                resultSet.getInt(EntityFieldColumnNames.AGE.getColumnIndex())
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
                "   %s %s, " +
                "   %s %s" +
                ");",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(), EntityFieldColumnNames.ID.getProperties(),
            EntityFieldColumnNames.FIRST_NAME.getColumnName(), EntityFieldColumnNames.FIRST_NAME.getProperties(),
            EntityFieldColumnNames.LAST_NAME.getColumnName(), EntityFieldColumnNames.LAST_NAME.getProperties(),
            EntityFieldColumnNames.COUNTRY.getColumnName(), EntityFieldColumnNames.COUNTRY.getProperties(),
            EntityFieldColumnNames.AGE.getColumnName(), EntityFieldColumnNames.AGE.getProperties()
        );

        return databaseProvider.createPreparedStatement(
            createTableIfNotExistsSql
        );
    }

    @Override
    public Pair<Connection, PreparedStatement> getInsertStatement(final Athlete entity) {
        final String insertEntitySql = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(),
            EntityFieldColumnNames.FIRST_NAME.getColumnName(),
            EntityFieldColumnNames.LAST_NAME.getColumnName(),
            EntityFieldColumnNames.COUNTRY.getColumnName(),
            EntityFieldColumnNames.AGE.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            insertEntitySql,
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getCountry(),
            entity.getAge()
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
    protected Pair<Connection, PreparedStatement> getUpdateStatement(final Athlete entity) {
        final String updateEntitySql = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s=?  WHERE %s = ?",
            tableName,
            EntityFieldColumnNames.FIRST_NAME.getColumnName(),
            EntityFieldColumnNames.LAST_NAME.getColumnName(),
            EntityFieldColumnNames.COUNTRY.getColumnName(),
            EntityFieldColumnNames.AGE.getColumnName(),
            EntityFieldColumnNames.ID.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            updateEntitySql,
            entity.getFirstName(),
            entity.getLastName(),
            entity.getCountry(),
            entity.getAge(),
            entity.getId()
        );
    }

    private enum EntityFieldColumnNames {

        ID(1, "id", "INT PRIMARY KEY NOT NULL"),
        FIRST_NAME(2, "firstName", "VARCHAR(20) NOT NULL"),
        LAST_NAME(3, "lastName", "VARCHAR(20) NOT NULL"),
        COUNTRY(4, "country", "VARCHAR(20) NOT NULL"),
        AGE(5, "age", "INT NOT NULL");

        private final String columnName;
        private final String properties;
        private final int columnIndex;

        EntityFieldColumnNames(final int columnIndex, final String columnName, final String properties) {
            this.columnName = columnName;
            this.properties = properties;
            this.columnIndex = columnIndex;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getProperties() {
            return properties;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

    }

}