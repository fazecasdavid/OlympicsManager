package ro.ubb.olympics.repository.jdbc.impl;

import ro.ubb.olympics.domain.Participation;
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
 * Participation JDBC Repository
 */
public class ParticipationJdbcRepository extends AbstractJdbcRepository<Long, Participation> {

    /**
     * Initializes the repository with the given validator, database provider and table name.
     *
     * @param validator        the validator used to validate the stored entities.
     * @param databaseProvider the database provider used by the repository.
     * @param tableName        the name of the table on which the repository operates.
     */
    public ParticipationJdbcRepository(final Validator<Participation> validator, final DatabaseProvider databaseProvider, final String tableName) {
        super(validator, databaseProvider, tableName);
    }

    @Override
    protected Participation loadEntity(final ResultSet resultSet) {
        try {
            return new Participation(
                resultSet.getLong(EntityFieldColumnNames.ID.getColumnIndex()),
                resultSet.getLong(EntityFieldColumnNames.ATHLETE_ID.getColumnIndex()),
                resultSet.getLong(EntityFieldColumnNames.COMPETITION_ID.getColumnIndex()),
                resultSet.getInt(EntityFieldColumnNames.RANK.getColumnIndex())
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
                "   %s %s" +
                ");",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(), EntityFieldColumnNames.ID.getProperties(),
            EntityFieldColumnNames.ATHLETE_ID.getColumnName(), EntityFieldColumnNames.ATHLETE_ID.getProperties(),
            EntityFieldColumnNames.COMPETITION_ID.getColumnName(), EntityFieldColumnNames.COMPETITION_ID.getProperties(),
            EntityFieldColumnNames.RANK.getColumnName(), EntityFieldColumnNames.RANK.getProperties()
        );

        return databaseProvider.createPreparedStatement(
            createTableIfNotExistsSql
        );
    }

    @Override
    protected Pair<Connection, PreparedStatement> getInsertStatement(final Participation entity) {
        final String insertEntitySql = String.format(
            "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(),
            EntityFieldColumnNames.ATHLETE_ID.getColumnName(),
            EntityFieldColumnNames.COMPETITION_ID.getColumnName(),
            EntityFieldColumnNames.RANK.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            insertEntitySql,
            entity.getId(),
            entity.getAthleteId(),
            entity.getCompetitionId(),
            entity.getRank()
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
    protected Pair<Connection, PreparedStatement> getUpdateStatement(final Participation entity) {
        final String updateEntitySql = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
            tableName,
            EntityFieldColumnNames.ATHLETE_ID.getColumnName(),
            EntityFieldColumnNames.COMPETITION_ID.getColumnName(),
            EntityFieldColumnNames.RANK.getColumnName(),
            EntityFieldColumnNames.ID.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            updateEntitySql,
            entity.getAthleteId(),
            entity.getCompetitionId(),
            entity.getRank(),
            entity.getId()
        );
    }

    private enum EntityFieldColumnNames {

        ID(1, "id", "INT PRIMARY KEY NOT NULL"),
        ATHLETE_ID(3, "athleteId", "INT NOT NULL REFERENCES Athlete"),
        COMPETITION_ID(2, "competitionId", "INT NOT NULL REFERENCES Competition"),
        RANK(4, "rank", "INT NOT NULL");

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