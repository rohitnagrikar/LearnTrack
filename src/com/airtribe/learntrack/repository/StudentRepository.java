package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.ArrayList;

/** Stores students in memory using an ArrayList. */
public class StudentRepository {
    private ArrayList<Student> students = new ArrayList<>();

    public void add(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getAll() {
        return students;
    }

    /** Returns the student, or null if no student has this ID. */
    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void remove(Student student) {
        students.remove(student);
    }
}
