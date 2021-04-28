package model;

import java.util.ArrayList;
import java.util.List;

public class CourseDirectory {
    private List<Course> courseList;

    public CourseDirectory(){
        courseList = new ArrayList<>();
    }

    public void addCourse(Course course){
        courseList.add(course);
    }

    public Course getCourse(int index){
        if(index >= courseList.size() - 1) throw  new IllegalArgumentException("CourseDirectory - getCourse: index beyond bound");
        return courseList.get(index);
    }

    public boolean deleteCourse(int index){
        if(index >= courseList.size() - 1) throw  new IllegalArgumentException("CourseDirectory - getCourse: index beyond bound");
        return deleteCourse(courseList.get(index));
    }

    public boolean deleteCourse(Course course){
        return courseList.remove(course);
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public CourseDirectory(List<Course> courseList) {
        this.courseList = courseList;
    }
}
