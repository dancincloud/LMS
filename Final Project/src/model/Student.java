package model;

/**
 *
 * @author Ke
 */
public class Student {
    private String studentID;
    private String name;
    private double gpa;
    private String email;
    private CourseDirectory coursedirectory;
    private String username;
    private String password;

    public Student(String studentID, String name, double gpa, String email, CourseDirectory coursedirectory, String username, String password) {
        this.studentID = studentID;
        this.name = name;
        this.gpa = gpa;
        this.email = email;
        this.coursedirectory = coursedirectory;
        this.username = username;
        this.password = password;
    }
    

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CourseDirectory getCoursedirectory() {
        return coursedirectory;
    }

    public void setCoursedirectory(CourseDirectory coursedirectory) {
        this.coursedirectory = coursedirectory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
