package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.ArrayList;

/** Stores courses in memory using an ArrayList. */
public class CourseRepository {
    private ArrayList<Course> courses = new ArrayList<>();

    public void add(Course course) {
        courses.add(course);
    }

    public ArrayList<Course> getAll() {
        return courses;
    }

    /** Returns the course, or null if no course has this ID. */
    public Course findById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }
}
