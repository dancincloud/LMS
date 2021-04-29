/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DBUtil.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ke
 */
public class Data implements Runnable {
    public Data() {}

    public User authenticateUser(String username, String password, int type) {
        if (type == 1) {
            // search in db
            Instructor instructor = InstructorDBUtil.selectByUsername(username);

            return instructor;
        } else {
            // search in db
            Student student = StudentDBUtil.selectByUsername(username);

            return student;

//            try {
//                List<Student> list = StudentDBUtil.selectAll();
//                for (Student student : list) {
//                    if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
//                        return student;
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            }


//            // test
//            for (Student student : students) {
//                if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
//                    return student;
//                }
//            }
//            return null;
        }
    }

    @Override
    public void run() {
        DBIntializer.main();

        List<String> studentIDs = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String id = "" + (i + 1);
            String name = DataGenerator.generateName(4, 6);
            String email = DataGenerator.generateEmailByName(name);
            if (i == 0) {
                id = "1";
                name = "s";
            }
            Student student = new Student(id, name, email, name, name, DataGenerator.generateGPA());

            students.add(student);
            studentIDs.add(student.getId());
        }

        List<Instructor> instructors = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String id = DataGenerator.generateID();
            String name = DataGenerator.generateName(4, 6);
            String email = DataGenerator.generateEmailByName(name);
            if (i == 0) {
                id = "1";
                name = "t";
            }

            Instructor instructor = new Instructor(id, name, email, name, name);

            instructors.add(instructor);
        }


        List<ZoomMeeting> zoomMeetings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String name = DataGenerator.generateName(4, 6);
            String link = DataGenerator.generateZoom();
            ZoomMeeting zoomMeeting = new ZoomMeeting("" + i, name, link);
            zoomMeetings.add(zoomMeeting);
            ZoomMeetingDBUtil.add(zoomMeeting);
        }



        List<File> files = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String name = DataGenerator.generateName(4, 6);
            String link = DataGenerator.generateFilePath(name);
            File file = new File("" + i, name, link);
            files.add(file);
            FileDBUtil.add(file);
        }


        List<Assignment> assignments = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            String name = DataGenerator.generateName(4, 6);
//            String content = DataGenerator.generateName(20, 50);
//            String link = DataGenerator.generateFilePath(name);
//            assignments.add(new Assignment("" + i, name, content, Assignment.AssignmentType.PROJECT));
//        }
        assignments.add(new Assignment("Final Project", "CSYE6200 Factory Assignment\n" +
                "\n" +
                " Take the explosion work (of your own): ExplosionAPI super class API specifying a single void explode() method, Derived classes of GunShot and Grenade implementing ExplosionAPI\n" +
                "Create and use Simple Factory class to create all derived objects;\n" +
                "Create and use GoF Factor Pattern classes (two) to create derived objects (two): this requires an ExplosionFactoryAPI super class so each GoF Factory Pattern class can be derived from it.\n" +
                "20 POINTS DEDUCTION IF iNCORRECT Submission: Submit ON TIME your entire eclipse workspace (single .zip file) ", Assignment.AssignmentType.PROJECT)
        );
        assignments.add(new Assignment("Midterm", "Design a Person class with the following attributes:\n" +
                "1. Person ID\n" +
                "2. Age\n" +
                "3. First Name\n" +
                "4. Last Name\n" +
                "5. Description\n" +
                "AND with AT LEAST the following methods:\n" +
                "6. A non-static toString() object instance method to return a String describing the object state (i.e. the values of all the data members associated with this object)\n" +
                "7. A static Demo() method which used for demonstrating the use of this class.", Assignment.AssignmentType.EXAM));
        AssignmentDBUtil.add(assignments.get(0));
        AssignmentDBUtil.add(assignments.get(1));


        List<String> courseIDs = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Course course = new Course(DataGenerator.generateCourseID(), DataGenerator.generateName(4, 8), "1");
            course.setAssignmentDirectory(new AssignmentDirectory(assignments));
            course.setFileDirectory(new FileDirectory(files));
            course.setZoomMeetingDirectory(new ZoomMeetingDirectory(zoomMeetings));
            course.setInstructorID("1");
            courses.add(course);
            course.setStudentIDs(studentIDs);
            CourseDBUtil.add(course);

            courseIDs.add(course.getCourseID());
        }



        for(Student student : students){
            student.setCourseIDs(courseIDs);
            StudentDBUtil.add(student);
        }



        for(Instructor instructor : instructors){
            for(Course course : courses){
                if (instructor.getId().equals(course.getInstructorID())){
                    instructor.getCourseIDs().add(course.getCourseID());
                }
            }
            InstructorDBUtil.add(instructor);
        }
    }
}
