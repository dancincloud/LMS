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
                " `courseDirectory`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, student.getId());
            stat.setString(2, student.getName());
            stat.setString(3, student.getEmail());
            stat.setString(4, student.getUsername());
            stat.setString(5, student.getPassword());
            stat.setDouble(6, student.getGpa());

            if (student.getCoursedirectory() != null) {
                stat.setString(7, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getList()));
            } else {
                stat.setString(7, null);
            }

            stat.setString(8, student.getUsername());
            stat.setString(9, student.getPassword());

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
                " `courseDirectory`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

                if (student.getCoursedirectory() != null) {
                    stat.setString(7, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getList()));
                } else {
                    stat.setString(7, null);
                }

                stat.setString(8, student.getUsername());
                stat.setString(9, student.getPassword());

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
            Student underDeletionStudent = select(studentID);
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                if (course.getStudentDirectory() != null) {
                    List<Student> students = course.getStudentDirectory().getList();
                    students.remove(underDeletionStudent);
                    CourseDBUtil.update(course);
                }
            }

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
                Student underDeletionStudent = select(studentID);
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    if (course.getStudentDirectory() != null) {
                        List<Student> students = course.getStudentDirectory().getList();
                        students.remove(underDeletionStudent);
                        CourseDBUtil.update(course);
                    }
                }
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
                course.setStudentDirectory(null);
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
                " courseDirectory = ?, updateTime = ? where studentID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, student.getName());
            stat.setString(2, student.getEmail());
            stat.setString(3, student.getUsername());
            stat.setString(4, student.getPassword());
            stat.setDouble(5, student.getGpa());


            if (student.getCoursedirectory() != null) {
                stat.setString(6, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getList()));
            } else {
                stat.setString(6, null);
            }

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

    public static boolean update(List<Student> students) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Student set name = ?, email = ?, username = ?, password = ?, gpa = ?, " +
                " courseDirectory = ?, updateTime = ? where studentID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (Student student : students) {
                stat.setString(1, student.getName());
                stat.setString(2, student.getEmail());
                stat.setString(3, student.getUsername());
                stat.setString(4, student.getPassword());
                stat.setDouble(5, student.getGpa());


                if (student.getCoursedirectory() != null) {
                    stat.setString(6, DBUtil.changeCourseDirectoryToString(student.getCoursedirectory().getList()));
                } else {
                    stat.setString(6, null);
                }

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

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, studentID);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                CourseDirectory courseDirectory = null;
                if (resultSet.getString("courseDirectory") != null) {
                    courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                }

                double gpa = resultSet.getDouble("gpa");

                Student student = new Student(studentID, name, email, username, password, courseDirectory, gpa);

                student.setCreateTime(resultSet.getLong("createTime"));
                student.setUpdateTime(resultSet.getLong("updateTime"));

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
                    String email = resultSet.getString("email");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");

                    CourseDirectory courseDirectory = null;
                    if (resultSet.getString("courseDirectory") != null) {
                        courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                    }

                    double gpa = resultSet.getDouble("gpa");

                    Student student = new Student(studentID, name, email, username, password, courseDirectory, gpa);

                    student.setCreateTime(resultSet.getLong("createTime"));
                    student.setUpdateTime(resultSet.getLong("updateTime"));
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
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                CourseDirectory courseDirectory = null;
                if (resultSet.getString("courseDirectory") != null) {
                    courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                }

                double gpa = resultSet.getDouble("gpa");

                Student student = new Student(studentID, name, email, username, password, courseDirectory, gpa);

                student.setCreateTime(resultSet.getLong("createTime"));
                student.setUpdateTime(resultSet.getLong("updateTime"));
                res.add(student);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
