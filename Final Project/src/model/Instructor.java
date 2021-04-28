package model;

/**
 *
 * @author Ke
 */
public class Instructor extends User {
    private CourseDirectory coursedirectory;
    
    public Instructor(){}

    public Instructor(String id, String name, String email, String username, String password) {
        super(id, name, email, username, password);

        this.setType(1);
    }

    public CourseDirectory getCoursedirectory() {
        return coursedirectory;
    }

    public void setCoursedirectory(CourseDirectory coursedirectory) {
        this.coursedirectory = coursedirectory;
    }
}
