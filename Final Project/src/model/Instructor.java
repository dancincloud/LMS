package model;

public class Instructor {
    private String name;
    private String instructorID;
    private CourseDirectory coursedirectory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public CourseDirectory getCoursedirectory() {
        return coursedirectory;
    }

    public void setCoursedirectory(CourseDirectory coursedirectory) {
        this.coursedirectory = coursedirectory;
    }
}
