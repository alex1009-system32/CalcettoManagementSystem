package org.example.calcettomanagmentsystem.shared.exceptions;

/**
 * Exception thrown when a persistence-level operation fails.
 * <p>
 * This exception wraps lower-level database errors (like {@link java.sql.SQLException})
 * to provide a consistent error handling mechanism within the application.
 * </p>
 *
 * @author Senior Developer
 */
public class DataAccessException extends RuntimeException {
    /**
     * Constructs a new DataAccessException with the specified detail message.
     *
     * @param message The error message.
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new DataAccessException with a message and an underlying cause.
     *
     * @param message The error message.
     * @param cause The root cause of the exception.
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
