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
 * @since 1.0
 */
public class ValidationException extends Exception {
    /**
     * Constructs a new ValidationException with the specified descriptive error message.
     * <p>
     * This constructor is used when a business rule is violated, and the message 
     * should be clear enough for the end user to understand the corrective action.
     * </p>
     *
     * @param message A string explaining the validation failure.
     */
    public ValidationException(String message) {
        super(message);
    }
}
