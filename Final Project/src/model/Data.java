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
    private List<User> userList;
    private List<User> c1userList;
    private List<User> c2userList;
    private List<User> c3userList;
    
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
        userList = new ArrayList<User>();
        
        c1userList = new ArrayList<User>();
        c2userList = new ArrayList<User>();
        c3userList = new ArrayList<User>();
        
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
        
        userList.add(s1);
        userList.add(s2);
        
        c1userList.add(s1);
        c1userList.add(s2);
        c2userList.add(s1);
        c3userList.add(s2);
        
        UserDirectory c1sd = new UserDirectory(c1userList);
        UserDirectory c2sd = new UserDirectory(c2userList);
        UserDirectory c3sd = new UserDirectory(c3userList);
        
        c1.setUserDirectory(c1sd); 
        c2.setUserDirectory(c2sd);
        c3.setUserDirectory(c3sd);
        
        c1.setFileDirectory(c1fd);
        c2.setFileDirectory(c2fd);
        c3.setFileDirectory(c3fd);
    }
    
    public User authenticateUser(String username, String password, int type){
        if(type == 1){
            for (User user : userList){
                 if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                    return user;
                }
            }
        }else{
            for (User user : userList){
                 if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                    return user;
                }
            }
        }
        return null;
    }
}
