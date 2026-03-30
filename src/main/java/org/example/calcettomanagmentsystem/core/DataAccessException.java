package org.example.calcettomanagmentsystem.core;

/**
 * Exception thrown when a persistence-level operation fails.
 * <p>
 * This exception wraps lower-level database errors (like {@link java.sql.SQLException})
 * to provide a consistent error handling mechanism within the application.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class DataAccessException extends RuntimeException {
    /**
     * Constructs a new DataAccessException with a specific detail message.
     * <p>
     * Use this constructor when a high-level error occurs that doesn't 
     * wrap a specific underlying cause.
     * </p>
     *
     * @param message A descriptive error message explaining the failure.
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new DataAccessException with a message and an underlying cause.
     * <p>
     * This constructor is preferred when wrapping a {@link java.sql.SQLException} 
     * or other low-level persistence error.
     * </p>
     *
     * @param message A descriptive error message explaining the failure.
     * @param cause The root cause that triggered this exception.
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
