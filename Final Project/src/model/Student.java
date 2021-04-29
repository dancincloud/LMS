package model;

/**
 *
 * @author Ke
 */
public class Student extends User {
    private double gpa;
    private CourseDirectory coursedirectory;

    public Student(String id, String name, String email, String username, String password, CourseDirectory coursedirectory,  double gpa) {
        super(id, name, email, username, password);

        this.setType(0);

        this.gpa = gpa;
        this.coursedirectory = coursedirectory;
    }

    public Student(String id, String name, String email, String username, String password,  double gpa) {
        super(id, name, email, username, password);

        this.setType(0);

        this.gpa = gpa;
    }

    // old version constructor
    public Student(String id, String name, double gpa, String email, CourseDirectory coursedirectory, String username, String password) {
        super(id, name, email, username, password);

        this.gpa = gpa;
        this.coursedirectory = coursedirectory;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public CourseDirectory getCoursedirectory() {
        if(coursedirectory == null) coursedirectory = new CourseDirectory();
        return coursedirectory;
    }

    public void setCoursedirectory(CourseDirectory coursedirectory) {
        this.coursedirectory = coursedirectory;
    }
}
