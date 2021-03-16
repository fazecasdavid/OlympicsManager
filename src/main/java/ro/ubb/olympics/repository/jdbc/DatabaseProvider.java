package ro.ubb.olympics.repository.jdbc;

import lombok.Data;
import ro.ubb.olympics.exception.SqlException;
import ro.ubb.olympics.utils.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

/**
 * Database provider.
 * It holds the database credentials and useful methods for DB interaction.
 */
@Data
public class DatabaseProvider {

    private final String url;
    private final String user;
    private final String password;

    /**
     * Create and return a PreparedStatement and its associated connection based on an SQL string and the
     * list of values that will replace the placeholders in the SQL string.
     *
     * @param sql    the SQL string to be used in the statement
     * @param values the values to replace the placeholders in the SQL string
     * @return a pair containing a PreparedStatement and its associated connection representing the required SQL string
     */
    public Pair<Connection, PreparedStatement> createPreparedStatement(final String sql, final Object... values) {
        try {
            final Connection connection = DriverManager.getConnection(url, user, password);
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);

            IntStream
                .rangeClosed(1, values.length)
                .forEach(index -> {
                    try {
                        preparedStatement.setObject(index, values[index - 1]);
                    } catch (SQLException sqlException) {
                        throw new SqlException(sqlException);
                    }
                });

            return new Pair<>(connection, preparedStatement);
        } catch (SQLException sqlException) {
            throw new SqlException(sqlException);
        }
    }

}