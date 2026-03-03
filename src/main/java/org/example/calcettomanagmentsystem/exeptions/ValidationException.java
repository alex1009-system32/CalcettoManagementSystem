package org.example.calcettomanagmentsystem.exeptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
