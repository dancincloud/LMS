package DBUtil;

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
        String sql = "INSERT INTO Student (`studentID`, `name`, `gpa`, `email`, `courseDirectory`) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, student.getStudentID());
            stat.setString(2, student.getName());
            stat.setDouble(3, student.getGpa());
            stat.setString(4, student.getEmail());
            stat.setString(5, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getCourseList()));

            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<Student> students) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Student (`studentID`, `name`, `gpa`, `email`, `courseDirectory`) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (Student student : students) {
                stat.setString(1, student.getStudentID());
                stat.setString(2, student.getName());
                stat.setDouble(3, student.getGpa());
                stat.setString(4, student.getEmail());
                stat.setString(5, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getCourseList()));
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
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Student student) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Student set name = ?, gpa = ?, email = ?, courseDirectory = ? where studentID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, student.getName());
            stat.setDouble(2, student.getGpa());
            stat.setString(3, student.getEmail());
            stat.setString(4, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getCourseList()));
            stat.setString(5, student.getStudentID());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<Student> students) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Student set name = ?, gpa = ?, email = ?, courseDirectory = ? where studentID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (Student student : students) {
                stat.setString(1, student.getName());
                stat.setDouble(2, student.getGpa());
                stat.setString(3, student.getEmail());
                stat.setString(4, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getCourseList()));
                stat.setString(5, student.getStudentID());
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
        Student student = new Student();

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, studentID);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                student.setStudentID(resultSet.getString("studentID"));
                student.setName(resultSet.getString("name"));
                student.setGpa(resultSet.getDouble("gpa"));
                student.setEmail(resultSet.getString("email"));

                CourseDirectory courseDirectory = new CourseDirectory();
                courseDirectory.setCourseList(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                student.setCoursedirectory(courseDirectory);
            }

            DBConnection.closeDB(conn, stat, resultSet);
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public static List<Student> select(List<String> studentIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Student where studentID = ?";
        List<Student> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String studentID : studentIDs) {
                Student student = new Student();
                student.setStudentID(studentID);

                stat.setString(1, studentID);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    student.setName(resultSet.getString("name"));
                    student.setGpa(resultSet.getDouble("gpa"));
                    student.setEmail(resultSet.getString("email"));

                    CourseDirectory courseDirectory = new CourseDirectory();
                    courseDirectory.setCourseList(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                    student.setCoursedirectory(courseDirectory);

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
                Student student = new Student();
                student.setStudentID(resultSet.getString("studentID"));
                student.setName(resultSet.getString("name"));
                student.setGpa(resultSet.getDouble("gpa"));
                student.setEmail(resultSet.getString("email"));

                CourseDirectory courseDirectory = new CourseDirectory();
                courseDirectory.setCourseList(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                student.setCoursedirectory(courseDirectory);

                res.add(student);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
