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
 * @author : [张天祺]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2021/4/26 8:32]
 * @updateUser : [张天祺]
 * @updateTime : [2021/4/26 8:32]
 * @updateRemark : [说明本次修改内容]
 */
public class RecordDBUtil {

    public static void add(Record record) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Record (`name`, `link`) VALUES (?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, record.getName());
            stat.setString(2, record.getLink());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<Record> records) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Record (`name`, `link`) VALUES (?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (Record record : records) {
                stat.setString(1, record.getName());
                stat.setString(2, record.getLink());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String name) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Record where name = ?";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, name);
            stat.executeUpdate();

            // update courses
            Record underDeletionRecord = select(name);
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                List<Record> records = course.getRecordDirectory().getRecordList();
                records.remove(underDeletionRecord);
                CourseDBUtil.update(course);
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(List<String> names) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Record where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (String name : names) {
                stat.setString(1, name);
                stat.executeUpdate();

                // update courses
                Record underDeletionRecord = select(name);
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    List<Record> records = course.getRecordDirectory().getRecordList();
                    records.remove(underDeletionRecord);
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
        String sql = "delete from Record";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Record record) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Record set link = ? where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, record.getLink());
            stat.setString(2, record.getName());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<Record> records) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Record set link = ? where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (Record record : records) {
                stat.setString(1, record.getLink());
                stat.setString(2, record.getName());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Record select(String name) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Record where name = ?";
        Record record = new Record();

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, name);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                record.setName(resultSet.getString("name"));
                record.setLink(resultSet.getString("link"));
            }

            DBConnection.closeDB(conn, stat, resultSet);
            return record;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return record;
    }

    public static List<Record> select(List<String> names) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Record where name = ?";
        List<Record> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String name : names) {
                Record record = new Record();
                record.setName(name);

                stat.setString(1, name);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    record.setLink(resultSet.getString("link"));
                    res.add(record);
                }
            }
            DBConnection.closeDB(conn, stat, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static List<Record> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Record";
        List<Record> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                Record record = new Record();
                record.setName(resultSet.getString("name"));
                record.setLink(resultSet.getString("link"));
                res.add(record);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

}
