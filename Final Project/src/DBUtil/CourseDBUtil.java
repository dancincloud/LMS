package DBUtil;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Ethan Zhang
 * @description : Database utilities for Course class
 * @createTime : [2021/4/25 0:04]
 */
public class CourseDBUtil {

    public static void add(Course course) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Course (`name`, `courseID`, `studentDirectory`, `fileDirectory`) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, course.getName());
            stat.setString(2, course.getCourseID());

            if (course.getStudentDirectory() != null) {
                stat.setString(3, DBUtil.changeStudentDirectoryToString(course.getStudentDirectory().getStudentList()));
            } else {
                stat.setString(3, null);
            }

            if (course.getFileDirectory() != null) {
                stat.setString(4, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getFileList()));
            } else {
                stat.setString(4, null);
            }

            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<Course> courses) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Course (`name`, `courseID`, `studentDirectory`, `fileDirectory`) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (Course course : courses) {
                stat.setString(1, course.getName());
                stat.setString(2, course.getCourseID());

                if (course.getStudentDirectory() != null) {
                    stat.setString(3, DBUtil.changeStudentDirectoryToString(course.getStudentDirectory().getStudentList()));
                } else {
                    stat.setString(3, null);
                }

                if (course.getFileDirectory() != null) {
                    stat.setString(4, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getFileList()));
                } else {
                    stat.setString(4, null);
                }

                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String courseID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Course where courseID = ?";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, courseID);
            stat.executeUpdate();

            Course underDeletionCourse = select(courseID);
            // update instructors
            List<Instructor> instructors = InstructorDBUtil.selectAll();
            for (Instructor instructor : instructors) {
                if (instructor.getCoursedirectory() != null) {
                    List<Course> courses = instructor.getCoursedirectory().getCourseList();
                    courses.remove(underDeletionCourse);
                    InstructorDBUtil.update(instructor);
                }
            }

            // update students
            List<Student> students = StudentDBUtil.selectAll();
            for (Student student : students) {
                if (student.getCoursedirectory() != null) {
                    List<Course> courses = student.getCoursedirectory().getCourseList();
                    courses.remove(underDeletionCourse);
                    StudentDBUtil.update(student);
                }
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(List<String> courseIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Course where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (String courseID : courseIDs) {
                stat.setString(1, courseID);
                stat.executeUpdate();

                Course underDeletionCourse = select(courseID);
                // update instructors
                List<Instructor> instructors = InstructorDBUtil.selectAll();
                for (Instructor instructor : instructors) {
                    if (instructor.getCoursedirectory() != null) {
                        List<Course> courses = instructor.getCoursedirectory().getCourseList();
                        courses.remove(underDeletionCourse);
                        InstructorDBUtil.update(instructor);
                    }
                }

                // update students
                List<Student> students = StudentDBUtil.selectAll();
                for (Student student : students) {
                    if (student.getCoursedirectory() != null) {
                        List<Course> courses = student.getCoursedirectory().getCourseList();
                        courses.remove(underDeletionCourse);
                        StudentDBUtil.update(student);
                    }
                }
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Course";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);

            // update instructors
            List<Instructor> instructors = InstructorDBUtil.selectAll();
            for (Instructor instructor : instructors) {
                instructor.setCoursedirectory(null);
            }
            InstructorDBUtil.update(instructors);

            // update students
            List<Student> students = StudentDBUtil.selectAll();
            for (Student student : students) {
                student.setCoursedirectory(null);
            }
            StudentDBUtil.update(students);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Course course) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Course set name = ?, studentDirectory = ?, fileDirectory = ? where courseID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, course.getName());

            if (course.getStudentDirectory() != null) {
                stat.setString(2, DBUtil.changeStudentDirectoryToString(course.getStudentDirectory().getStudentList()));
            } else {
                stat.setString(2, null);
            }

            if (course.getFileDirectory() != null) {
                stat.setString(3, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getFileList()));
            } else {
                stat.setString(3, null);
            }

            stat.setString(4, course.getCourseID());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<Course> courses) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Course set name = ?, studentDirectory = ?, fileDirectory = ? where courseID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (Course course : courses) {
                stat.setString(1, course.getName());

                if (course.getStudentDirectory() != null) {
                    stat.setString(2, DBUtil.changeStudentDirectoryToString(course.getStudentDirectory().getStudentList()));
                } else {
                    stat.setString(2, null);
                }

                if (course.getFileDirectory() != null) {
                    stat.setString(3, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getFileList()));
                } else {
                    stat.setString(3, null);
                }

                stat.setString(4, course.getCourseID());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Course select(String courseID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course where courseID = ?";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, courseID);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()){
                Course course = new Course(resultSet.getString("name") ,resultSet.getString("courseID"));

                if (resultSet.getString("studentDirectory") != null) {
                    StudentDirectory studentDirectory = new StudentDirectory(StudentDBUtil.select(Arrays.asList(resultSet.getString("studentDirectory").split("\\|"))));
                    course.setStudentDirectory(studentDirectory);
                }


                if (resultSet.getString("fileDirectory") != null) {
                    FileDirectory fileDirectory = new FileDirectory(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                    course.setFileDirectory(fileDirectory);
                }

                DBConnection.closeDB(conn, stat, resultSet);
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Course> select(List<String> courseIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course where name = ?";
        List<Course> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String courseID : courseIDs) {
                stat.setString(1, courseID);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    Course course = new Course(resultSet.getString("name") ,resultSet.getString("courseID"));

                    if (resultSet.getString("studentDirectory") != null) {
                        StudentDirectory studentDirectory = new StudentDirectory(StudentDBUtil.select(Arrays.asList(resultSet.getString("studentDirectory").split("\\|"))));
                        course.setStudentDirectory(studentDirectory);
                    }


                    if (resultSet.getString("fileDirectory") != null) {
                        FileDirectory fileDirectory = new FileDirectory(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                        course.setFileDirectory(fileDirectory);
                    }

                    res.add(course);
                }
            }

            DBConnection.closeDB(conn, stat, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static List<Course> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course";
        List<Course> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                Course course = new Course(resultSet.getString("name") ,resultSet.getString("courseID"));

                if (resultSet.getString("studentDirectory") != null) {
                    StudentDirectory studentDirectory = new StudentDirectory(StudentDBUtil.select(Arrays.asList(resultSet.getString("studentDirectory").split("\\|"))));
                    course.setStudentDirectory(studentDirectory);
                }

                if (resultSet.getString("fileDirectory") != null) {
                    FileDirectory fileDirectory = new FileDirectory(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                    course.setFileDirectory(fileDirectory);
                }

                res.add(course);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void main(String[] args) {
//        Course course = new Course("CSYE", "6200");
//        List<Course> courses = new ArrayList<>();
//        courses.add(course);
//
//        Student student1 = new Student("0001", "EthanZhang", 4.0, "1111@111.com", new CourseDirectory(courses), "123", "456");
//        Student student2 = new Student("0002", "DaHuiLang", 3.9, "2222@222.com", new CourseDirectory(courses), "456", "789");
//        List<Student> students = new ArrayList<>();
//        students.add(student1);
//        students.add(student2);
//
//        course.setStudentDirectory(new StudentDirectory(students));
//
//        CourseDBUtil.add(course);
//        StudentDBUtil.add(student1);
//        StudentDBUtil.add(student2);
//
//        List<String> studentIDs = new ArrayList<>();
//        studentIDs.add("0001");
//        studentIDs.add("0002");
//        StudentDBUtil.delete(studentIDs);
    }
}
