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
    public static boolean add(Student student) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Student (`studentID`, `name`, `email`, `username`, `password`, `gpa`, " +
                " `courseIDs`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, student.getId());
            stat.setString(2, student.getName());
            stat.setString(3, student.getEmail());
            stat.setString(4, student.getUsername());
            stat.setString(5, student.getPassword());
            stat.setDouble(6, student.getGpa());
            stat.setString(7, DBUtil.mergeFields(student.getCourseIDs()));
            stat.setLong(8, System.currentTimeMillis());
            stat.setLong(9, -1L);

            stat.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean add(List<Student> students) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Student (`studentID`, `name`, `email`, `username`, `password`, `gpa`, " +
                " `courseIDs`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (Student student : students) {
                stat.setString(1, student.getId());
                stat.setString(2, student.getName());
                stat.setString(3, student.getEmail());
                stat.setString(4, student.getUsername());
                stat.setString(5, student.getPassword());
                stat.setDouble(6, student.getGpa());
                stat.setString(7, DBUtil.mergeFields(student.getCourseIDs()));
                stat.setLong(8, System.currentTimeMillis());
                stat.setLong(9, -1L);

                stat.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean delete(String studentID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Student where studentID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, studentID);
            stat.executeUpdate();

            // update courses
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                course.getStudentIDs().remove(studentID);
            }
            CourseDBUtil.update(courses);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean delete(List<String> studentIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Student where studentID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (String studentID : studentIDs) {
                stat.setString(1, studentID);
                stat.executeUpdate();

                // update courses
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    course.getStudentIDs().remove(studentID);
                }
                CourseDBUtil.update(courses);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean deleteAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Student";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.executeUpdate();

            // update courses
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                course.setStudentIDs(null);
            }
            CourseDBUtil.update(courses);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(Student student) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Student set name = ?, email = ?, username = ?, password = ?, gpa = ?, " +
                " courseIDs = ?, updateTime = ? where studentID = ?";

        System.out.println(sql);
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, student.getName());
            stat.setString(2, student.getEmail());
            stat.setString(3, student.getUsername());
            stat.setString(4, student.getPassword());
            stat.setDouble(5, student.getGpa());
            stat.setString(6, DBUtil.mergeFields(student.getCourseIDs()));
            stat.setLong(7, System.currentTimeMillis());
            stat.setString(8, student.getId());
            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean updateCreateTime(String studentID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Student set createTime = ? where studentID = ?";

        System.out.println(sql);
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setLong(1, System.currentTimeMillis());
            stat.setString(2, studentID);
            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(List<Student> students) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Student set name = ?, email = ?, username = ?, password = ?, gpa = ?, " +
                " courseIDs = ?, updateTime = ? where studentID = ?";
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(sql);
            for (Student student : students) {
                stat.setString(1, student.getName());
                stat.setString(2, student.getEmail());
                stat.setString(3, student.getUsername());
                stat.setString(4, student.getPassword());
                stat.setDouble(5, student.getGpa());
                stat.setString(6, DBUtil.mergeFields(student.getCourseIDs()));
                stat.setLong(7, System.currentTimeMillis());
                stat.setString(8, student.getId());
                stat.executeUpdate();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static Student select(String studentID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Student where studentID = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, studentID);
            resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                double gpa = resultSet.getDouble("gpa");
                Student student = new Student(studentID, name, email, username, password, gpa);

                student.setCourseIDs(Arrays.asList(resultSet.getString("courseIDs").split("\\|")));

                student.setCreateTime(resultSet.getLong("createTime"));
                student.setUpdateTime(resultSet.getLong("updateTime"));

                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return null;
    }

    public static Student selectByUsername(String username) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Student where username = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, username);
            resultSet = stat.executeQuery();

            if (resultSet.next()){
                String studentID = resultSet.getString("studentID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                double gpa = resultSet.getDouble("gpa");
                Student student = new Student(studentID, name, email, username, password, gpa);

                if (resultSet.getString("courseIDs") != null) {
                    student.setCourseIDs(Arrays.asList(resultSet.getString("courseIDs").split("\\|")));
                }

                student.setCreateTime(resultSet.getLong("createTime"));
                student.setUpdateTime(resultSet.getLong("updateTime"));

                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return null;
    }

    public static List<Student> select(List<String> studentIDs) {

        if (studentIDs == null) {
            return new ArrayList<>();
        }

        if (studentIDs.size() == 0) {
            return new ArrayList<>();
        }

        if(studentIDs == null) return new ArrayList<>();

        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Student where studentID = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<Student> res = new ArrayList<>();

        try {
            stat = conn.prepareStatement(sql);
            resultSet = null;
            for (String studentID : studentIDs) {
                stat.setString(1, studentID);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    double gpa = resultSet.getDouble("gpa");
                    Student student = new Student(studentID, name, email, username, password, gpa);

                    student.setCourseIDs(Arrays.asList(resultSet.getString("courseIDs").split("\\|")));

                    student.setCreateTime(resultSet.getLong("createTime"));
                    student.setUpdateTime(resultSet.getLong("updateTime"));
                    res.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return res;
    }

    public static List<Student> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Student";
        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<Student> res = new ArrayList<>();

        try {
            stat = conn.prepareStatement(sql);
            resultSet = stat.executeQuery();

            while (resultSet.next()){
                String studentID = resultSet.getString("studentID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                double gpa = resultSet.getDouble("gpa");
                Student student = new Student(studentID, name, email, username, password, gpa);

                student.setCourseIDs(Arrays.asList(resultSet.getString("courseIDs").split("\\|")));

                student.setCreateTime(resultSet.getLong("createTime"));
                student.setUpdateTime(resultSet.getLong("updateTime"));
                res.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return res;
    }
}
