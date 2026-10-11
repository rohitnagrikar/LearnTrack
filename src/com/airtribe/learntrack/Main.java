package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;
import java.util.Scanner;

/** Menu-driven console app. Main only shows menus, reads input and calls the services. */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentService enrollmentService =
            new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        System.out.println("=== Welcome to LearnTrack ===");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Enrollment Management");
            System.out.println("0. Exit");

            switch (readMenuChoice()) {
                case MenuOptions.STUDENT_MANAGEMENT:
                    studentMenu();
                    break;
                case MenuOptions.COURSE_MANAGEMENT:
                    courseMenu();
                    break;
                case MenuOptions.ENROLLMENT_MANAGEMENT:
                    enrollmentMenu();
                    break;
                case MenuOptions.EXIT:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose from the menu.");
            }
        }
        System.out.println("Goodbye!");
    }

    // ---------------- Student menu ----------------

    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student");
            System.out.println("5. Deactivate student");
            System.out.println("0. Back");

            try {
                switch (readMenuChoice()) {
                    case MenuOptions.ADD_STUDENT:
                        addStudent();
                        break;
                    case MenuOptions.VIEW_STUDENTS:
                        printStudents(studentService.listStudents());
                        break;
                    case MenuOptions.SEARCH_STUDENT:
                        int id = readInt("Enter student ID: ", "Student ID");
                        System.out.println(studentService.findStudentById(id));
                        break;
                    case MenuOptions.UPDATE_STUDENT:
                        updateStudent();
                        break;
                    case MenuOptions.DEACTIVATE_STUDENT:
                        studentService.deactivateStudent(readInt("Enter student ID: ", "Student ID"));
                        System.out.println("Student deactivated.");
                        break;
                    case MenuOptions.BACK:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addStudent() throws InvalidInputException {
        String firstName = readText("First name: ");
        String lastName = readText("Last name: ");
        String email = readText("Email (press Enter to skip): ");
        String batch = readText("Batch: ");

        Student student;
        if (InputValidator.isEmpty(email)) {
            student = studentService.addStudent(firstName, lastName, batch);
        } else {
            student = studentService.addStudent(firstName, lastName, email, batch);
        }
        System.out.println("Student added: " + student);
    }

    private static void updateStudent() throws EntityNotFoundException, InvalidInputException {
        int id = readInt("Enter student ID to update: ", "Student ID");
        studentService.findStudentById(id); // fail early if the ID does not exist
        String firstName = readText("New first name: ");
        String lastName = readText("New last name: ");
        String email = readText("New email: ");
        String batch = readText("New batch: ");
        studentService.updateStudent(id, firstName, lastName, email, batch);
        System.out.println("Student updated.");
    }

    // ---------------- Course menu ----------------

    private static void courseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add new course");
            System.out.println("2. View all courses");
            System.out.println("3. Activate course");
            System.out.println("4. Deactivate course");
            System.out.println("0. Back");

            try {
                switch (readMenuChoice()) {
                    case MenuOptions.ADD_COURSE:
                        addCourse();
                        break;
                    case MenuOptions.VIEW_COURSES:
                        printCourses(courseService.listCourses());
                        break;
                    case MenuOptions.ACTIVATE_COURSE:
                        courseService.activateCourse(readInt("Enter course ID: ", "Course ID"));
                        System.out.println("Course activated.");
                        break;
                    case MenuOptions.DEACTIVATE_COURSE:
                        courseService.deactivateCourse(readInt("Enter course ID: ", "Course ID"));
                        System.out.println("Course deactivated.");
                        break;
                    case MenuOptions.BACK:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addCourse() throws InvalidInputException {
        String name = readText("Course name: ");
        String description = readText("Description: ");
        int weeks = readInt("Duration in weeks: ", "Duration");
        Course course = courseService.addCourse(name, description, weeks);
        System.out.println("Course added: " + course);
    }

    // ---------------- Enrollment menu ----------------

    private static void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll a student in a course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. Mark enrollment as completed");
            System.out.println("4. Mark enrollment as cancelled");
            System.out.println("0. Back");

            try {
                switch (readMenuChoice()) {
                    case MenuOptions.ENROLL_STUDENT:
                        int studentId = readInt("Student ID: ", "Student ID");
                        int courseId = readInt("Course ID: ", "Course ID");
                        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
                        System.out.println("Enrolled: " + enrollment);
                        break;
                    case MenuOptions.VIEW_STUDENT_ENROLLMENTS:
                        int id = readInt("Student ID: ", "Student ID");
                        printEnrollments(enrollmentService.getEnrollmentsForStudent(id));
                        break;
                    case MenuOptions.MARK_COMPLETED:
                        enrollmentService.markCompleted(readInt("Enrollment ID: ", "Enrollment ID"));
                        System.out.println("Enrollment marked as COMPLETED.");
                        break;
                    case MenuOptions.MARK_CANCELLED:
                        enrollmentService.markCancelled(readInt("Enrollment ID: ", "Enrollment ID"));
                        System.out.println("Enrollment marked as CANCELLED.");
                        break;
                    case MenuOptions.BACK:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------------- Printing helpers ----------------

    private static void printStudents(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void printCourses(ArrayList<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void printEnrollments(ArrayList<Enrollment> enrollments) {
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for this student.");
            return;
        }
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    // ---------------- Input helpers ----------------

    /** Reads one line. If the input has ended (e.g. Ctrl+D), returns "0" so the menus exit cleanly. */
    private static String readText(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return "0";
        }
        return scanner.nextLine();
    }

    /** Reads a menu number. Anything that is not a number becomes -1 (an invalid option). */
    private static int readMenuChoice() {
        String text = readText("Choose an option: ");
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int readInt(String prompt, String fieldName) throws InvalidInputException {
        return InputValidator.parseInt(readText(prompt), fieldName);
    }
}
