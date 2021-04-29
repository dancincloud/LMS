package model;

import java.util.List;

/**
 *
 * @author Ke
 */
public class Instructor extends User {
    private List<String> courseIDs;
    private CourseDirectory coursedirectory;
    
    public Instructor(){}

    public Instructor(String id, String name, String email, String username, String password) {
        super(id, name, email, username, password);

        this.setType(1);

        this.coursedirectory = new CourseDirectory();
    }

    public void addCourse(Course course){
        coursedirectory.add(course);
    }

    public CourseDirectory getCoursedirectory() {
        return coursedirectory;
    }

    public void setCoursedirectory(CourseDirectory coursedirectory) {
        this.coursedirectory = coursedirectory;
    }

    public List<String> getCourseIDs() {
        return courseIDs;
    }

    public void setCourseIDs(List<String> courseIDs) {
        this.courseIDs = courseIDs;
    }
}
