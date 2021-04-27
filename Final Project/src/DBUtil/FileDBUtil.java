package DBUtil;

import model.Course;
import model.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ethan Zhang
 * @description : Database utilities for File class
 * @createTime : [2021/4/26 8:25]
 */
public class FileDBUtil {

    public static void add(File file) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO File (`name`, `link`) VALUES (?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, file.getName());
            stat.setString(2, file.getLink());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<File> files) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO File (`name`, `link`) VALUES (?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (File file : files) {
                stat.setString(1, file.getName());
                stat.setString(2, file.getLink());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String name) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from File where name = ?";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, name);
            stat.executeUpdate();

            // update courses
            File underDeletionFile = select(name);
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                List<File> files = course.getFileDirectory().getFileList();
                files.remove(underDeletionFile);
                CourseDBUtil.update(course);
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(List<String> names) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from File where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (String name : names) {
                stat.setString(1, name);
                stat.executeUpdate();

                // update courses
                File underDeletionFile = select(name);
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    List<File> files = course.getFileDirectory().getFileList();
                    files.remove(underDeletionFile);
                    CourseDBUtil.update(course);
                }
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from File";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);

            // update courses
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                course.setFileDirectory(null);
            }
            CourseDBUtil.update(courses);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(File file) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update File set link = ? where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, file.getLink());
            stat.setString(2, file.getName());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<File> files) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update File set link = ? where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (File file : files) {
                stat.setString(1, file.getLink());
                stat.setString(2, file.getName());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File select(String name) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from File where name = ?";
        File file = new File(null);

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, name);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                file.setName(resultSet.getString("name"));
                file.setLink(resultSet.getString("link"));
            }

            DBConnection.closeDB(conn, stat, resultSet);
            return file;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static List<File> select(List<String> names) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from File where name = ?";
        List<File> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String name : names) {

                stat.setString(1, name);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    File file = new File(name);
                    file.setLink(resultSet.getString("link"));
                    res.add(file);
                }
            }
            DBConnection.closeDB(conn, stat, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static List<File> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from File";
        List<File> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                File file = new File(resultSet.getString("name"));
                file.setLink(resultSet.getString("link"));
                res.add(file);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

}
