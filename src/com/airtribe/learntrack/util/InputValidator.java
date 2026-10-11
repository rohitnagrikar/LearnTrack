package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

/** Simple helper methods to check user input. */
public class InputValidator {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static void requireText(String value, String fieldName) throws InvalidInputException {
        if (isEmpty(value)) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
    }

    public static void requireEmail(String email) throws InvalidInputException {
        if (isEmpty(email) || !email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Please enter a valid email (example: name@mail.com).");
        }
    }

    public static void requirePositive(int value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be greater than 0.");
        }
    }

    /** Converts text to an int, or throws a friendly exception instead of crashing. */
    public static int parseInt(String text, String fieldName) throws InvalidInputException {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a whole number.");
        }
    }
}
