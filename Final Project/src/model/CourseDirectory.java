package model;

import java.util.ArrayList;
import java.util.List;

public class CourseDirectory extends Directory<Course> {
    public CourseDirectory(){
        super();
    }

    public CourseDirectory(List<Course> courseList) {
        super(courseList);
    }
}
