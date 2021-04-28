package model;

import java.util.ArrayList;
import java.util.List;

public class CourseDirectory extends Directory<Course> {
    public List<Course> getCourseList() {
        return this.getList();
    }

    public void setCourseList(List<Course> courseList) {
        this.setList(courseList);
    }

    public CourseDirectory(){
        super();
    }

    public CourseDirectory(List<Course> courseList) {
        super(courseList);
    }
}
