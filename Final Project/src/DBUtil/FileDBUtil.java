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

    public static boolean add(File file) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO File (`fileID`, `name`, `link`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, file.getFileID());
            stat.setString(2, file.getName());
            stat.setString(3, file.getLink());
            stat.setLong(4, System.currentTimeMillis());
            stat.setLong(5, -1L);
            stat.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean add(List<File> files) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO File (`fileID`, `name`, `link`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (File file : files) {
                stat.setString(1, file.getFileID());
                stat.setString(2, file.getName());
                stat.setString(3, file.getLink());
                stat.setLong(4, System.currentTimeMillis());
                stat.setLong(5, -1L);
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

    public static boolean delete(String fileID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from File where fileID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, fileID);
            stat.executeUpdate();

            // update courses
            File underDeletionFile = select(fileID);
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                if (course.getFileDirectory() != null) {
                    List<File> files = course.getFileDirectory().getList();
                    files.remove(underDeletionFile);
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

    public static boolean delete(List<String> fileIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from File where fileID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (String fileID : fileIDs) {
                stat.setString(1, fileID);
                stat.executeUpdate();

                // update courses
                File underDeletionFile = select(fileID);
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    if (course.getFileDirectory() != null) {
                        List<File> files = course.getFileDirectory().getList();
                        files.remove(underDeletionFile);
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
        String sql = "delete from File";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.executeUpdate();

            // update courses
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                course.setFileDirectory(null);
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

    public static boolean update(File file) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update File set name = ?, link = ?, updateTime = ? where fileID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, file.getName());
            stat.setString(2, file.getLink());
            stat.setLong(3, System.currentTimeMillis());
            stat.setString(4, file.getFileID());
            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(List<File> files) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update File set name = ?, link = ?, updateTime = ? where fileID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (File file : files) {
                stat.setString(1, file.getName());
                stat.setString(2, file.getLink());
                stat.setLong(3, System.currentTimeMillis());
                stat.setString(4, file.getFileID());
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

    public static File select(String fileID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from File where fileID = ?";

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, fileID);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String link = resultSet.getString("link");
                File file = new File(fileID, name, link);
                file.setCreateTime(resultSet.getLong("createTime"));
                file.setUpdateTime(resultSet.getLong("updateTime"));
                DBConnection.closeDB(conn, stat, resultSet);
                return file;
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<File> select(List<String> fileIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from File where fileID = ?";
        List<File> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String fileID : fileIDs) {
                stat.setString(1, fileID);
                resultSet = stat.executeQuery();

                if (resultSet.next()){
                    String name = resultSet.getString("name");
                    String link = resultSet.getString("link");
                    File file = new File(fileID, name, link);
                    file.setCreateTime(resultSet.getLong("createTime"));
                    file.setUpdateTime(resultSet.getLong("updateTime"));

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
                String fileID = resultSet.getString("fileID");
                String name = resultSet.getString("name");
                String link = resultSet.getString("link");
                File file = new File(fileID, name, link);
                file.setCreateTime(resultSet.getLong("createTime"));
                file.setUpdateTime(resultSet.getLong("updateTime"));
                res.add(file);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

}
