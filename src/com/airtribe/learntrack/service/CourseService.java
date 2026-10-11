package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;

/** Business logic for courses. */
public class CourseService {
    private CourseRepository repository = new CourseRepository();

    public Course addCourse(String courseName, String description, int durationInWeeks)
            throws InvalidInputException {
        InputValidator.requireText(courseName, "Course name");
        InputValidator.requireText(description, "Description");
        InputValidator.requirePositive(durationInWeeks, "Duration");

        Course course = new Course(IdGenerator.getNextCourseId(), courseName.trim(),
                description.trim(), durationInWeeks);
        repository.add(course);
        return course;
    }

    public ArrayList<Course> listCourses() {
        return repository.getAll();
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        Course course = repository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("No course found with ID " + id + ".");
        }
        return course;
    }

    public void activateCourse(int id) throws EntityNotFoundException {
        findCourseById(id).setActive(true);
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        findCourseById(id).setActive(false);
    }
}
