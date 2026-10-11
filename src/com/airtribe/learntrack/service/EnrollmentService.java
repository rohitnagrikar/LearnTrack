package com.airtribe.learntrack.service;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import java.time.LocalDate;
import java.util.ArrayList;

/** Business logic for enrollments. Uses the student and course services to check IDs. */
public class EnrollmentService {
    private EnrollmentRepository repository = new EnrollmentRepository();
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId)
            throws EntityNotFoundException, InvalidInputException {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (!student.isActive()) {
            throw new InvalidInputException("This student is inactive and cannot be enrolled.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("This course is inactive and cannot accept enrollments.");
        }
        for (Enrollment existing : repository.getAll()) {
            if (existing.getStudentId() == studentId && existing.getCourseId() == courseId
                    && existing.getStatus().equals(AppConstants.STATUS_ACTIVE)) {
                throw new InvalidInputException("This student is already enrolled in this course.");
            }
        }

        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(), studentId,
                courseId, LocalDate.now().toString(), AppConstants.STATUS_ACTIVE);
        repository.add(enrollment);
        return enrollment;
    }

    public ArrayList<Enrollment> getEnrollmentsForStudent(int studentId)
            throws EntityNotFoundException {
        studentService.findStudentById(studentId); // throws if the student does not exist
        ArrayList<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : repository.getAll()) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }

    public void markCompleted(int enrollmentId)
            throws EntityNotFoundException, InvalidInputException {
        changeStatus(enrollmentId, AppConstants.STATUS_COMPLETED);
    }

    public void markCancelled(int enrollmentId)
            throws EntityNotFoundException, InvalidInputException {
        changeStatus(enrollmentId, AppConstants.STATUS_CANCELLED);
    }

    private void changeStatus(int enrollmentId, String newStatus)
            throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = repository.findById(enrollmentId);
        if (enrollment == null) {
            throw new EntityNotFoundException("No enrollment found with ID " + enrollmentId + ".");
        }
        if (!enrollment.getStatus().equals(AppConstants.STATUS_ACTIVE)) {
            throw new InvalidInputException("Only ACTIVE enrollments can be changed. This one is "
                    + enrollment.getStatus() + ".");
        }
        enrollment.setStatus(newStatus);
    }
}
