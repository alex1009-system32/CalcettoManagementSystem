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
 * @version 0.1
 * @since 1.0
 */
public interface DataBaseSource {
    /**
     * Provides a connection to the database.
     * <p>
     * Implementations should handle connection pooling or singleton management 
     * to ensure efficient resource usage.
     * </p>
     *
     * @return A {@link Connection} object for interacting with the database.
     * @throws SQLException If a database access error occurs during connection retrieval.
     */
    Connection getConnection() throws SQLException;

    /**
     * Initializes the database schema.
     * <p>
     * This typically involves creating tables, indices, and setting up initial data 
     * required for the application to function correctly.
     * </p>
     */
    void init();
}
