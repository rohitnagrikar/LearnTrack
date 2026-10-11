package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;

/** Business logic for students. */
public class StudentService {
    private StudentRepository repository = new StudentRepository();

    /** Adds a student with an email. */
    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        InputValidator.requireText(firstName, "First name");
        InputValidator.requireText(lastName, "Last name");
        InputValidator.requireText(batch, "Batch");
        InputValidator.requireEmail(email);

        Student student = new Student(IdGenerator.getNextStudentId(), firstName.trim(),
                lastName.trim(), email.trim(), batch.trim());
        repository.add(student);
        return student;
    }

    /** Overloaded: adds a student without an email. */
    public Student addStudent(String firstName, String lastName, String batch)
            throws InvalidInputException {
        InputValidator.requireText(firstName, "First name");
        InputValidator.requireText(lastName, "Last name");
        InputValidator.requireText(batch, "Batch");

        Student student = new Student(IdGenerator.getNextStudentId(), firstName.trim(),
                lastName.trim(), batch.trim());
        repository.add(student);
        return student;
    }

    public ArrayList<Student> listStudents() {
        return repository.getAll();
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        Student student = repository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("No student found with ID " + id + ".");
        }
        return student;
    }

    public void updateStudent(int id, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(id);
        InputValidator.requireText(firstName, "First name");
        InputValidator.requireText(lastName, "Last name");
        InputValidator.requireText(batch, "Batch");
        InputValidator.requireEmail(email);

        student.setFirstName(firstName.trim());
        student.setLastName(lastName.trim());
        student.setEmail(email.trim());
        student.setBatch(batch.trim());
    }

    /** Soft delete: the student stays in the list but is marked inactive. */
    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(false);
    }

    /** Hard delete: removes the student from the list completely. */
    public void removeStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        repository.remove(student);
    }
}
