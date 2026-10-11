package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import java.util.ArrayList;

/** Stores enrollments in memory using an ArrayList. */
public class EnrollmentRepository {
    private ArrayList<Enrollment> enrollments = new ArrayList<>();

    public void add(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public ArrayList<Enrollment> getAll() {
        return enrollments;
    }

    /** Returns the enrollment, or null if no enrollment has this ID. */
    public Enrollment findById(int id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        return null;
    }
}
