package org.example.calcettomanagmentsystem.core.connection.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface defining the requirements for a database connection source.
 * <p>
 * This abstraction allows the application to remain agnostic of the specific
 * database implementation (e.g., SQLite, MySQL).
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public interface DataBaseSource {
    /**
     * Provides a connection to the database.
     *
     * @return A {@link Connection} object for interacting with the database.
     * @throws SQLException If a database access error occurs.
     */
    Connection getConnection() throws SQLException;

    /**
     * Initializes the database schema.
     * This typically involves creating tables and setting up initial data.
     */
    void init();
}
