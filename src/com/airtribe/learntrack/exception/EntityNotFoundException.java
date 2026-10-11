package com.airtribe.learntrack.exception;

/** Thrown when a Student, Course or Enrollment with the given ID does not exist. */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
