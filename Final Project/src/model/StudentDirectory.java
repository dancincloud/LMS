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
public class StudentDirectory {
    
    private List<Student> studentList;

    public StudentDirectory(List<Student> studentList) {
        this.studentList = studentList;
    }


    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
    
    /*public Student authenticateUser(String username, String password){
        for (Student student : studentList)
            if (student.getUsername().equals(username) && student.getPassword().equals(password)){
                return student;
            }
        return null;
    }*/
   
}
