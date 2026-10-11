package com.airtribe.learntrack.exception;

/** Thrown when the user gives bad input or breaks a business rule. */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
