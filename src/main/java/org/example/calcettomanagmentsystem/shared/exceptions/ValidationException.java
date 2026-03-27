package org.example.calcettomanagmentsystem.shared.exceptions;

/**
 * Exception thrown when domain-level data validation fails.
 * <p>
 * This class is used to communicate errors related to business rule violations
 * (e.g., empty names, invalid email formats) back to the user interface.
 * </p>
 *
 * @author Senior Developer
 */
public class ValidationException extends RuntimeException {
    /**
     * Constructs a new ValidationException with the specified message.
     *
     * @param message The validation error message.
     */
    public ValidationException(String message) {
        super(message);
    }
}
