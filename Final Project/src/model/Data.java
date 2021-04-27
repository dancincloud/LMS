/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ke
 */
public class Data {
    private List<Student> studentList;
    private List<Course> courseList;
 
    Course c1 = new Course("OOD","CSYE6200");
    Course c2 = new Course("Application Engineer","INFO5100");

    public Data() {
        studentList = new ArrayList<Student>();
        courseList = new ArrayList<Course>();
        courseList.add(c1);
        courseList.add(c2);
        CourseDirectory cd1 = new CourseDirectory(courseList);
        Student s1 = new Student("1","1",1,"1",cd1,"s","s");
        studentList.add(s1);
        StudentDirectory sd1 = new StudentDirectory(studentList);
        c1.setStudentDirectory(sd1); 
        c2.setStudentDirectory(sd1);
    }
    
    public Student authenticateUser(String username, String password){
        for (Student student : studentList)
            if (student.getUsername().equals(username) && student.getPassword().equals(password)){
                return student;
            }
        return null;
    }
}
