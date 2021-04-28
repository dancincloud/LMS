package DBUtil;

import model.Course;
import model.CourseDirectory;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Ethan Zhang
 * @description : [一句话描述该类的功能]
 * @createTime : [2021/4/26 9:00]
 */
public class StudentDBUtil {
    public static void add(Student student) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Student (`studentID`, `name`, `gpa`, `email`, `courseDirectory`, `username`, `password`) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, student.getId());
            stat.setString(2, student.getName());
            stat.setDouble(3, student.getGpa());
            stat.setString(4, student.getEmail());

            if (student.getCoursedirectory() != null) {
                stat.setString(5, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getCourseList()));
            } else {
                stat.setString(5, null);
            }

            stat.setString(6, student.getUsername());
            stat.setString(7, student.getPassword());

            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<Student> students) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Student (`studentID`, `name`, `gpa`, `email`, `courseDirectory`, `username`, `password`) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (Student student : students) {
                stat.setString(1, student.getId());
                stat.setString(2, student.getName());
                stat.setDouble(3, student.getGpa());
                stat.setString(4, student.getEmail());

                if (student.getCoursedirectory() != null) {
                    stat.setString(5, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getCourseList()));
                } else {
                    stat.setString(5, null);
                }

                stat.setString(6, student.getUsername());
                stat.setString(7, student.getPassword());

                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String studentID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Student where studentID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, studentID);
            stat.executeUpdate();

            // update courses
            Student underDeletionStudent = select(studentID);
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                if (course.getStudentDirectory() != null) {
                    List<Student> students = course.getStudentDirectory().getStudentList();
                    students.remove(underDeletionStudent);
                    CourseDBUtil.update(course);
                }
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(List<String> studentIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Student where studentID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (String studentID : studentIDs) {
                stat.setString(1, studentID);
                stat.executeUpdate();

                // update courses
                Student underDeletionStudent = select(studentID);
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    if (course.getStudentDirectory() != null) {
                        List<Student> students = course.getStudentDirectory().getStudentList();
                        students.remove(underDeletionStudent);
                        CourseDBUtil.update(course);
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
        String sql = "delete from Student";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();

            // update courses
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                course.setStudentDirectory(null);
            }
            CourseDBUtil.update(courses);

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Student student) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Student set name = ?, gpa = ?, email = ?, courseDirectory = ?, username = ?, password = ? where studentID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, student.getName());
            stat.setDouble(2, student.getGpa());
            stat.setString(3, student.getEmail());

            if (student.getCoursedirectory() != null) {
                stat.setString(4, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getCourseList()));
            } else {
                stat.setString(4, null);
            }

            stat.setString(5, student.getUsername());
            stat.setString(6, student.getPassword());
            stat.setString(7, student.getId());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<Student> students) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Student set name = ?, gpa = ?, email = ?, courseDirectory = ?, username = ?, password = ? where studentID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (Student student : students) {
                stat.setString(1, student.getName());
                stat.setDouble(2, student.getGpa());
                stat.setString(3, student.getEmail());

                if (student.getCoursedirectory() != null) {
                    stat.setString(4, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getCourseList()));
                } else {
                    stat.setString(4, null);
                }

                stat.setString(5, student.getId());
                stat.setString(6, student.getPassword());
                stat.setString(7, student.getId());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Student select(String studentID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Student where studentID = ?";


        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, studentID);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                double gpa = resultSet.getDouble("gpa");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                CourseDirectory courseDirectory = null;
                if (resultSet.getString("courseDirectory") != null) {
                    courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                }

                Student student = new Student(studentID, name, gpa, email, courseDirectory, username, password);
                DBConnection.closeDB(conn, stat, resultSet);

                return student;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Student> select(List<String> studentIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Student where studentID = ?";
        List<Student> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String studentID : studentIDs) {
                stat.setString(1, studentID);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    double gpa = resultSet.getDouble("gpa");
                    String email = resultSet.getString("email");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");

                    CourseDirectory courseDirectory = null;
                    if (resultSet.getString("courseDirectory") != null) {
                        courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                    }

                    Student student = new Student(studentID, name, gpa, email, courseDirectory, username, password);
                    res.add(student);
                }
            }
            DBConnection.closeDB(conn, stat, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static List<Student> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Student";
        List<Student> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                String studentID = resultSet.getString("studentID");
                String name = resultSet.getString("name");
                double gpa = resultSet.getDouble("gpa");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                CourseDirectory courseDirectory = null;
                if (resultSet.getString("courseDirectory") != null) {
                    courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                }

                Student student = new Student(studentID, name, gpa, email, courseDirectory, username, password);
                res.add(student);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
