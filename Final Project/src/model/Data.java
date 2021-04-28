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
    private List<Student> c1studentList;
    private List<Student> c2studentList;
    private List<Student> c3studentList;
    
    private List<Course> s1courseList;
    private List<Course> s2courseList;
    
    private List<File> c1fileList;
    private List<File> c2fileList;
    private List<File> c3fileList;

    private List<Instructor> instructors;
    
    File f1 = new File("JavaDataTypes.ppt");
    File f2 = new File("JavaClassSyntax.ppt");
    File f3 = new File("University-Example.ppt");
    File f4 = new File("Syntax.docx");
    
    Course c1 = new Course("OOD","CSYE6200");
    Course c2 = new Course("Application Engineer","INFO5100");
    Course c3 = new Course("Career Management","ENCP6000");
   

    public Data() {
        studentList = new ArrayList<Student>();
        
        c1studentList = new ArrayList<Student>();
        c2studentList = new ArrayList<Student>();
        c3studentList = new ArrayList<Student>();
        
        s1courseList = new ArrayList<Course>();
        s2courseList = new ArrayList<Course>();
        
        s1courseList.add(c1);
        s1courseList.add(c2);
        s2courseList.add(c1);
        s2courseList.add(c3);
        
        CourseDirectory cd1 = new CourseDirectory(s1courseList);
        CourseDirectory cd2 = new CourseDirectory(s2courseList);
        
        c1fileList = new ArrayList<File>();
        c2fileList = new ArrayList<File>();
        c3fileList = new ArrayList<File>();
        
        c1fileList.add(f1);
        c1fileList.add(f2);
        c2fileList.add(f3);
        c3fileList.add(f4);
        
        FileDirectory c1fd = new FileDirectory(c1fileList);
        FileDirectory c2fd = new FileDirectory(c2fileList);
        FileDirectory c3fd = new FileDirectory(c3fileList);

        Student s1 = new Student("0001","Alaric",4.5,"ke.zh@northeastern.edu",cd1,"s1","s1");
        Student s2 = new Student("0002","Joan",4.8,"joan@northeastern.edu",cd2,"s2","s2");
        
        studentList.add(s1);
        studentList.add(s2);
        
        c1studentList.add(s1);
        c1studentList.add(s2);
        c2studentList.add(s1);
        c3studentList.add(s2);
        
        StudentDirectory c1sd = new StudentDirectory(c1studentList);
        StudentDirectory c2sd = new StudentDirectory(c2studentList);
        StudentDirectory c3sd = new StudentDirectory(c3studentList);
        
        c1.setStudentDirectory(c1sd); 
        c2.setStudentDirectory(c2sd);
        c3.setStudentDirectory(c3sd);
        
        c1.setFileDirectory(c1fd);
        c2.setFileDirectory(c2fd);
        c3.setFileDirectory(c3fd);


        instructors = new ArrayList<>();

        instructors.add(new Instructor("T1", "t",  MockDataGenerator.generateEmailByName("t"), "t", "t"));
        for(int i = 0; i < 5; i++){
            String id = MockDataGenerator.generateID();
            String name = MockDataGenerator.generateName(4,6);
            String email = MockDataGenerator.generateEmailByName(name);
            Instructor instructor = new Instructor(id, name, email, name, name);
            instructors.add(instructor);

            System.out.println(instructor);
        }

    }
    
    public User authenticateUser(String username, String password, int type){
        if(type == 1){
            for (Instructor instructor : instructors){
                if (instructor.getUsername().equals(username) && instructor.getPassword().equals(password)){
                    return instructor;
                }
            }
            return null;
        }else{
            for (Student student : studentList){
                if (student.getUsername().equals(username) && student.getPassword().equals(password)){
                    return student;
                }
            }
            return null;
        }
    }
}
