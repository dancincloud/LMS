package model;

public class Student {
    private String studentID;
    private String name;
    private double gpa;
    private CourseDirectory coursedirectory;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public CourseDirectory getCoursedirectory() {
        return coursedirectory;
    }

    public void setCoursedirectory(CourseDirectory coursedirectory) {
        this.coursedirectory = coursedirectory;
    }
}
