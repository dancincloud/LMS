package DBUtil;

import model.Course;
import model.File;
import model.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ethan Zhang
 * @description : Database utilities for Record class
 * @createTime : [2021/4/26 8:32]
 */
public class RecordDBUtil {

    public static boolean add(Record record) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Record (`recordID`, `name`, `link`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, record.getRecordID());
            stat.setString(2, record.getName());
            stat.setString(3, record.getLink());
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

    public static boolean add(List<Record> records) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Record (`recordID`, `name`, `link`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(sql);
            for (Record record : records) {
                stat.setString(1, record.getRecordID());
                stat.setString(2, record.getName());
                stat.setString(3, record.getLink());
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

    public static boolean delete(String recordID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Record where recordID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, recordID);
            stat.executeUpdate();

            // update courses
            Record underDeletionRecord = select(recordID);
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                if (course.getRecordDirectory() != null) {
                    List<Record> records = course.getRecordDirectory().getList();
                    records.remove(underDeletionRecord);
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

    public static boolean delete(List<String> recordIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Record where recordID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (String recordID : recordIDs) {
                stat.setString(1, recordID);
                stat.executeUpdate();

                // update courses
                Record underDeletionRecord = select(recordID);
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    if (course.getRecordDirectory() != null) {
                        List<Record> records = course.getRecordDirectory().getList();
                        records.remove(underDeletionRecord);
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
        String sql = "delete from Record";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.executeUpdate();

            // update courses
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                course.setRecordDirectory(null);
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

    public static boolean update(Record record) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Record set name = ?, link = ?, updateTime = ? where recordID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, record.getName());
            stat.setString(2, record.getLink());
            stat.setLong(3, System.currentTimeMillis());
            stat.setString(4, record.getRecordID());
            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(List<Record> records) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Record set name = ?, link = ?, updateTime = ? where recordID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (Record record : records) {
                stat.setString(1, record.getName());
                stat.setString(2, record.getLink());
                stat.setLong(3, System.currentTimeMillis());
                stat.setString(4, record.getRecordID());
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

    public static Record select(String recordID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Record where recordID = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, recordID);
            resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String link = resultSet.getString("link");
                Record record = new Record(recordID, name, link);
                record.setCreateTime(resultSet.getLong("createTime"));
                record.setUpdateTime(resultSet.getLong("updateTime"));

                DBConnection.closeDB(conn, stat, resultSet);
                return record;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return null;
    }

    public static List<Record> select(List<String> recordIDs) {

        if (recordIDs == null) {
            return new ArrayList<>();
        }

        if (recordIDs.size() == 0) {
            return new ArrayList<>();
        }

        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Record where name = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<Record> res = new ArrayList<>();

        try {
            stat = conn.prepareStatement(sql);
            for (String recordID : recordIDs) {
                stat.setString(1, recordID);
                resultSet = stat.executeQuery();

                if (resultSet.next()){
                    String name = resultSet.getString("name");
                    String link = resultSet.getString("link");
                    Record record = new Record(recordID, name, link);
                    record.setCreateTime(resultSet.getLong("createTime"));
                    record.setUpdateTime(resultSet.getLong("updateTime"));
                    res.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return res;
    }

    public static List<Record> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Record";
        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<Record> res = new ArrayList<>();

        try {
            stat = conn.prepareStatement(sql);
            resultSet = stat.executeQuery();

            while (resultSet.next()){
                String recordID = resultSet.getString("recordID");
                String name = resultSet.getString("name");
                String link = resultSet.getString("link");
                Record record = new Record(recordID, name, link);
                record.setCreateTime(resultSet.getLong("createTime"));
                record.setUpdateTime(resultSet.getLong("updateTime"));
                res.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return res;
    }

}
