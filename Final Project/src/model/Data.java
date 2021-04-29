/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DBUtil.InstructorDBUtil;
import DBUtil.StudentDBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ke
 */
public class Data {
    private List<User> userList;
    private List<Student> c1userList;
    private List<Student> c2userList;
    private List<Student> c3userList;
    
    private List<Course> s1courseList;
    private List<Course> s2courseList;
    
    private List<File> c1fileList;
    private List<File> c2fileList;
    private List<File> c3fileList;

    private List<Instructor> instructors;
    private List<Student> students;
    
    private List<Assignment> c1assignment;
    private List<Assignment> c2assignment;
    private List<Assignment> c3assignment;
    
    File f1 = new File("JavaDataTypes.ppt");
    File f2 = new File("JavaClassSyntax.ppt");
    File f3 = new File("University-Example.ppt");
    File f4 = new File("Syntax.docx");
    
    Course c1 = new Course("OOD","CSYE6200");
    Course c2 = new Course("Application Engineer","INFO5100");
    Course c3 = new Course("Career Management","ENCP6000");
    
    Assignment a1 = new Assignment("Final Project","a",Assignment.AssignmentType.PROJECT);

    public Data() {
        userList = new ArrayList<User>();
        
        c1userList = new ArrayList<Student>();
        c2userList = new ArrayList<Student>();
        c3userList = new ArrayList<Student>();
        
        c1assignment = new ArrayList<Assignment>();
        c2assignment = new ArrayList<Assignment>();
        c3assignment = new ArrayList<Assignment>();
        
        s1courseList = new ArrayList<Course>();
        s2courseList = new ArrayList<Course>();
        
        s1courseList.add(c1);
        s1courseList.add(c2);
        s2courseList.add(c1);
        s2courseList.add(c3);
        
        c1assignment.add(a1);
        c2assignment.add(a1);
        c3assignment.add(a1);
        
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
        
        AssignmentDirectory ad1 = new AssignmentDirectory(c1assignment);
        AssignmentDirectory ad2 = new AssignmentDirectory(c2assignment);
        AssignmentDirectory ad3 = new AssignmentDirectory(c3assignment);

        Student s1 = new Student("0001","Alaric",4.5,"ke.zh@northeastern.edu",cd1,"s1","s1");
        Student s2 = new Student("0002","Joan",4.8,"joan@northeastern.edu",cd2,"s2","s2");
        
        userList.add(s1);
        userList.add(s2);
        
        c1userList.add(s1);
        c1userList.add(s2);
        c2userList.add(s1);
        c3userList.add(s2);
        
        StudentDirectory c1sd = new StudentDirectory(c1userList);
        StudentDirectory c2sd = new StudentDirectory(c2userList);
        StudentDirectory c3sd = new StudentDirectory(c3userList);
        
        c1.setStudentDirectory(c1sd);
        c2.setStudentDirectory(c2sd);
        c3.setStudentDirectory(c3sd);
        
        c1.setFileDirectory(c1fd);
        c2.setFileDirectory(c2fd);
        c3.setFileDirectory(c3fd);
        
        c1.setAssignmentDirectory(ad1);
        c2.setAssignmentDirectory(ad2);
        c3.setAssignmentDirectory(ad3);
        
        students = new ArrayList<>();
        students.add(s1);
        students.add(s2);



        List<ZoomMeeting> zoomMeetings = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            String name = DataGenerator.generateName(4,6);
            String link = DataGenerator.generateZoom();
            zoomMeetings.add(new ZoomMeeting("" + i, name, link));
        }

        List<File> files = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            String name = DataGenerator.generateName(4,6);
            String link = DataGenerator.generateFilePath(name);
            files.add(new File("" + i, name, link));
        }

        List<Assignment> assignments = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            String name = DataGenerator.generateName(4,6);
            String content = DataGenerator.generateName(20, 50);
            String link = DataGenerator.generateFilePath(name);
            assignments.add(new Assignment("" + i, name, content, Assignment.AssignmentType.PROJECT));
        }


        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            Course course = new Course(DataGenerator.generateCourseID(), DataGenerator.generateName(4,8), "1");
            courses.add(course);
        }

        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            String id = DataGenerator.generateID();
            String name = DataGenerator.generateName(4,6);
            String email = DataGenerator.generateEmailByName(name);
            Student student = new Student(id, name, email, name, name, DataGenerator.generateGPA());

            for (Course course : courses) {
                student.getCoursedirectory().add(course);
            }

            students.add(student);
        }


        instructors = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            String id = DataGenerator.generateID();
            String name = DataGenerator.generateName(4,6);
            String email = DataGenerator.generateEmailByName(name);
            if(i == 0){
                id = "1";
                name = "t";
            }

            Instructor instructor = new Instructor(id, name, email, name, name);

            for (Course course : courses){
                instructor.addCourse(course);
            }

            instructors.add(instructor);

            System.out.println(instructor);
        }


        // add all data to course
        for (Course course : courses) {
            for (Student student : students){
                course.getStudentDirectory().add(student);
            }

            for(File file : files){
                course.getFileDirectory().add(file);
            }

            for(Assignment assignment : assignments){
                course.getAssignmentDirectory().add(assignment);
            }

            for (ZoomMeeting zoomMeeting : zoomMeetings){
                course.getZoomMeetingDirectory().add(zoomMeeting);
            }
        }
    }
    
    public User authenticateUser(String username, String password, int type){
        if(type == 1){
            // search in db
            List<Instructor> list = InstructorDBUtil.selectAll();
            for (Instructor instructor : list){
                if (instructor.getUsername().equals(username) && instructor.getPassword().equals(password)){
                    return instructor;
                }
            }

            // test
            for (Instructor instructor : instructors){
                if (instructor.getUsername().equals(username) && instructor.getPassword().equals(password)){
                    return instructor;
                }
            }
            return null;
        }else{
            // search in db
            List<Student> list = StudentDBUtil.selectAll();
            for (Student student : list){
                if (student.getUsername().equals(username) && student.getPassword().equals(password)){
                    return student;
                }
            }

            // test
            for (Student student : students){
                if (student.getUsername().equals(username) && student.getPassword().equals(password)){
                    return student;
                }
            }
            return null;
        }
    }
}
