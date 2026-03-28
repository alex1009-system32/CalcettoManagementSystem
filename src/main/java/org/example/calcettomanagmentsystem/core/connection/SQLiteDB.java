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
 * @version 0.0
 */
public class SQLiteDB implements DataBaseSource {
    /** Configuration properties for the SQLite connection. */
    private static final Properties properties = new Properties();
    /** The singleton database connection instance. */
    private static java.sql.Connection connection;

    static {
        try (InputStream inputStream = SQLiteDB.class.getResourceAsStream(
                "/org/example/calcettomanagmentsystem/config/db.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("Properties file not found!");
            }

            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    /**
     * Obtains the shared database connection, opening it if necessary.
     *
     * @return The active {@link Connection}.
     * @throws SQLException If connection fails.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(properties.getProperty("db.sqlite.url"));
        }
        return connection;
    }

    /**
     * Initializes the database schema for standard operations.
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
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    /**
     * Initializes the database schema specifically for testing scenarios.
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
