package com.airtribe.learntrack.entity;

/** A student. Inherits id, name and email from Person. */
public class Student extends Person {
    private String batch;
    private boolean active;

    // Constructor with email
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    // Overloaded constructor: student without email
    public Student(int id, String firstName, String lastName, String batch) {
        this(id, firstName, lastName, "Not provided", batch);
    }

    public String getBatch() { return batch; }
    public boolean isActive() { return active; }

    public void setBatch(String batch) { this.batch = batch; }
    public void setActive(boolean active) { this.active = active; }

    // Overrides Person.getDisplayName()
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }

    @Override
    public String toString() {
        return "Student [ID: " + getId() + ", Name: " + getDisplayName()
                + ", Email: " + getEmail() + ", Active: " + active + "]";
    }
}
