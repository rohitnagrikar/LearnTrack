package com.airtribe.learntrack.entity;

/** Links one student to one course. Status is "ACTIVE", "COMPLETED" or "CANCELLED". */
public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;
    private String enrollmentDate;
    private String status;

    public Enrollment(int id, int studentId, int courseId, String enrollmentDate, String status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public String getEnrollmentDate() { return enrollmentDate; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Enrollment [ID: " + id + ", Student ID: " + studentId + ", Course ID: " + courseId
                + ", Date: " + enrollmentDate + ", Status: " + status + "]";
    }
}
