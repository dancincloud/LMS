package DBUtil;

import model.ZoomMeeting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ethan Zhang
 * @description : Database utilities for ZoomMeeting class
 * @createTime : [2021/4/26 8:32]
 */
public class ZoomMeetingDBUtil {

    public static void add(ZoomMeeting zoomMeeting) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO ZoomMeeting (`name`, `link`) VALUES (?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, zoomMeeting.getName());
            stat.setString(2, zoomMeeting.getLink());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<ZoomMeeting> zoomMeetings) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO ZoomMeeting (`name`, `link`) VALUES (?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (ZoomMeeting zoomMeeting : zoomMeetings) {
                stat.setString(1, zoomMeeting.getName());
                stat.setString(2, zoomMeeting.getLink());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String name) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from ZoomMeeting where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, name);
            stat.executeUpdate();

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(List<String> names) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from ZoomMeeting where name = ?";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (String name : names) {
                stat.setString(1, name);
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete ZoomMeeting Record";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(ZoomMeeting zoomMeeting) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update ZoomMeeting set link = ? where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, zoomMeeting.getLink());
            stat.setString(2, zoomMeeting.getName());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<ZoomMeeting> zoomMeetings) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update ZoomMeeting set link = ? where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (ZoomMeeting zoomMeeting : zoomMeetings) {
                stat.setString(1, zoomMeeting.getLink());
                stat.setString(2, zoomMeeting.getName());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ZoomMeeting select(String name) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from ZoomMeeting where name = ?";
        ZoomMeeting zoomMeeting = new ZoomMeeting();

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, name);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                zoomMeeting.setName(resultSet.getString("name"));
                zoomMeeting.setLink(resultSet.getString("link"));
            }

            DBConnection.closeDB(conn, stat, resultSet);
            return zoomMeeting;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zoomMeeting;
    }

    public static List<ZoomMeeting> select(List<String> names) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from ZoomMeeting where name = ?";
        List<ZoomMeeting> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String name : names) {
                ZoomMeeting zoomMeeting = new ZoomMeeting();
                zoomMeeting.setName(name);

                stat.setString(1, name);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    zoomMeeting.setLink(resultSet.getString("link"));
                    res.add(zoomMeeting);
                }
            }
            DBConnection.closeDB(conn, stat, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static List<ZoomMeeting> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from ZoomMeeting";
        List<ZoomMeeting> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                ZoomMeeting zoomMeeting = new ZoomMeeting();
                zoomMeeting.setName(resultSet.getString("name"));
                zoomMeeting.setLink(resultSet.getString("link"));
                res.add(zoomMeeting);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
