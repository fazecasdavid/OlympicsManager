package ro.ubb.olympics.repository.jdbc.impl;

import ro.ubb.olympics.domain.Sponsorship;
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
 * Sponsorship JDBC repository.
 */
public class SponsorshipJdbcRepository extends AbstractJdbcRepository<Long, Sponsorship> {

    /**
     * Initializes the repository with the given validator, database provider and table name.
     *
     * @param validator        the validator used to validate the stored entities.
     * @param databaseProvider the database provider used by the repository.
     * @param tableName        the name of the table on which the repository operates.
     */
    public SponsorshipJdbcRepository(final Validator<Sponsorship> validator, final DatabaseProvider databaseProvider, final String tableName) {
        super(validator, databaseProvider, tableName);
    }

    @Override
    protected Sponsorship loadEntity(final ResultSet resultSet) {
        try {
            return new Sponsorship(
                resultSet.getLong(EntityFieldColumnNames.ID.getColumnIndex()),
                resultSet.getLong(EntityFieldColumnNames.COMPETITION_ID.getColumnIndex()),
                resultSet.getLong(EntityFieldColumnNames.SPONSOR_ID.getColumnIndex()),
                resultSet.getInt(EntityFieldColumnNames.MONEY_CONTRIBUTION.getColumnIndex())
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
            EntityFieldColumnNames.COMPETITION_ID.getColumnName(), EntityFieldColumnNames.COMPETITION_ID.getProperties(),
            EntityFieldColumnNames.SPONSOR_ID.getColumnName(), EntityFieldColumnNames.SPONSOR_ID.getProperties(),
            EntityFieldColumnNames.MONEY_CONTRIBUTION.getColumnName(), EntityFieldColumnNames.MONEY_CONTRIBUTION.getProperties()
        );

        return databaseProvider.createPreparedStatement(
            createTableIfNotExistsSql
        );
    }

    @Override
    public Pair<Connection, PreparedStatement> getInsertStatement(final Sponsorship entity) {
        final String insertEntitySql = String.format(
            "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
            tableName,
            EntityFieldColumnNames.ID.getColumnName(),
            EntityFieldColumnNames.COMPETITION_ID.getColumnName(),
            EntityFieldColumnNames.SPONSOR_ID.getColumnName(),
            EntityFieldColumnNames.MONEY_CONTRIBUTION.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            insertEntitySql,
            entity.getId(),
            entity.getCompetitionId(),
            entity.getSponsorId(),
            entity.getMoneyContribution()
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
    protected Pair<Connection, PreparedStatement> getUpdateStatement(final Sponsorship entity) {
        final String updateEntitySql = String.format(
            "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
            tableName,
            EntityFieldColumnNames.COMPETITION_ID.getColumnName(),
            EntityFieldColumnNames.SPONSOR_ID.getColumnName(),
            EntityFieldColumnNames.MONEY_CONTRIBUTION.getColumnName(),
            EntityFieldColumnNames.ID.getColumnName()
        );

        return databaseProvider.createPreparedStatement(
            updateEntitySql,
            entity.getCompetitionId(),
            entity.getSponsorId(),
            entity.getMoneyContribution(),
            entity.getId()
        );
    }

    private enum EntityFieldColumnNames {

        ID(1, "id", "INT PRIMARY KEY NOT NULL"),
        COMPETITION_ID(2, "competitionId", "INT NOT NULL REFERENCES Competition"),
        SPONSOR_ID(3, "sponsorId", "INT NOT NULL REFERENCES Sponsor"),
        MONEY_CONTRIBUTION(4, "moneyContribution", "INT NOT NULL");

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