package org.example.calcettomanagmentsystem.service;

/**
 * Exception thrown when domain-level data validation fails.
 * <p>
 * This class is used to communicate errors related to business rule violations
 * (e.g., empty names, invalid email formats) back to the user interface.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 */
public class ValidationException extends Exception {
    /**
     * Constructs a new ValidationException with the specified message.
     *
     * @param message The validation error message.
     */
    public ValidationException(String message) {
        super(message);
    }
}
