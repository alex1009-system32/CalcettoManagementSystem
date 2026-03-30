package org.example.calcettomanagmentsystem.core.connection;

import org.example.calcettomanagmentsystem.core.connection.interfaces.DataBaseSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Implementation of {@link DataBaseSource} for SQLite database.
 * <p>
 * This class manages a singleton database connection and handles schema initialization
 * using SQL scripts loaded via {@link SQLReader}.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class SQLiteDB implements DataBaseSource {
    /** Configuration properties loaded from the db.properties resource. */
    private static final Properties properties = new Properties();
    /** The singleton database connection instance used throughout the application lifecycle. */
    private static java.sql.Connection connection;

    static {
        try (InputStream inputStream = SQLiteDB.class.getResourceAsStream(
                "/org/example/calcettomanagmentsystem/config/db.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("Properties file not found at expected location!");
            }

            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties from resource", e);
        }
    }

    /**
     * Obtains the shared database connection, opening it if it's currently null or closed.
     * <p>
     * This implementation uses the URL specified in the {@code db.sqlite.url} property.
     * </p>
     *
     * @return The active {@link Connection} to the SQLite database.
     * @throws SQLException If a database access error occurs or the URL is invalid.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(properties.getProperty("db.sqlite.url"));
        }
        return connection;
    }

    /**
     * Initializes the database schema using the standard setup script.
     * <p>
     * This method reads the SQL script defined by {@link SQLSchemaNavigator#SETUP} 
     * and executes each statement delimited by a semicolon.
     * </p>
     *
     * @throws RuntimeException If database access or SQL file reading fails.
     */
    public void init() {
        try {
            Connection conn = this.getConnection();
            String fullScript = SQLReader.readFile(SQLSchemaNavigator.SETUP);
            String[] statements = fullScript.split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String sql : statements) {
                    String trimmedSql = sql.trim();
                    if (!trimmedSql.isEmpty()) {
                        stmt.execute(trimmedSql);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Production database initialization failed", e);
        }
    }

    /**
     * Initializes the database schema specifically for testing scenarios with mock data.
     * <p>
     * This method reads the SQL script defined by {@link SQLSchemaNavigator#TEST_DB}
     * and executes each statement.
     * </p>
     *
     * @throws RuntimeException If database access or SQL file reading fails during test setup.
     */
    public void initTest() {
        try {
            Connection conn = this.getConnection();
            String fullScript = SQLReader.readFile(SQLSchemaNavigator.TEST_DB);
            String[] statements = fullScript.split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String sql : statements) {
                    String trimmedSql = sql.trim();
                    if (!trimmedSql.isEmpty()) {
                        stmt.execute(trimmedSql);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Test database initialization failed", e);
        }
    }
}
