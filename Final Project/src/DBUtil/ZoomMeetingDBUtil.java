package DBUtil;

import model.Course;
import model.Record;
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

    public static boolean add(ZoomMeeting zoomMeeting) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO ZoomMeeting (`zoomMeetingID`, `name`, `link`, `createTime`, `updateTime`) " +
                " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, zoomMeeting.getZoomMeetingID());
            stat.setString(2, zoomMeeting.getName());
            stat.setString(3, zoomMeeting.getLink());
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

    public static boolean add(List<ZoomMeeting> zoomMeetings) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO ZoomMeeting (`zoomMeetingID`, `name`, `link`, `createTime`, `updateTime`) " +
                " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (ZoomMeeting zoomMeeting : zoomMeetings) {
                stat.setString(1, zoomMeeting.getZoomMeetingID());
                stat.setString(2, zoomMeeting.getName());
                stat.setString(3, zoomMeeting.getLink());
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

    public static boolean delete(String zoomMeetingID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from ZoomMeeting where zoomMeetingID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, zoomMeetingID);
            stat.executeUpdate();

            // update courses
            ZoomMeeting underDeletionZoomMeeting = select(zoomMeetingID);
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                if (course.getZoomMeetingDirectory() != null) {
                    List<ZoomMeeting> zoomMeetings = course.getZoomMeetingDirectory().getList();
                    zoomMeetings.remove(underDeletionZoomMeeting);
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

    public static boolean delete(List<String> zoomMeetingIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from ZoomMeeting where zoomMeetingID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (String zoomMeetingID : zoomMeetingIDs) {
                stat.setString(1, zoomMeetingID);
                stat.executeUpdate();

                // update courses
                ZoomMeeting underDeletionZoomMeeting = select(zoomMeetingID);
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    if (course.getZoomMeetingDirectory() != null) {
                        List<ZoomMeeting> zoomMeetings = course.getZoomMeetingDirectory().getList();
                        zoomMeetings.remove(underDeletionZoomMeeting);
                        CourseDBUtil.update(course);
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean deleteAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete ZoomMeeting Record";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.executeUpdate();

            // update courses
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                course.setZoomMeetingDirectory(null);
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

    public static boolean update(ZoomMeeting zoomMeeting) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update ZoomMeeting set name = ?, link = ?, updateTime = ? where zoomMeetingID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, zoomMeeting.getName());
            stat.setString(2, zoomMeeting.getLink());
            stat.setLong(3, System.currentTimeMillis());
            stat.setString(4, zoomMeeting.getZoomMeetingID());
            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(List<ZoomMeeting> zoomMeetings) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update ZoomMeeting set name = ?, link = ?, updateTime = ? where zoomMeetingID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (ZoomMeeting zoomMeeting : zoomMeetings) {
                stat.setString(1, zoomMeeting.getName());
                stat.setString(2, zoomMeeting.getLink());
                stat.setLong(3, System.currentTimeMillis());
                stat.setString(4, zoomMeeting.getZoomMeetingID());
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

    public static ZoomMeeting select(String zoomMeetingID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from ZoomMeeting where zoomMeetingID = ?";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, zoomMeetingID);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String link = resultSet.getString("link");
                ZoomMeeting zoomMeeting = new ZoomMeeting(zoomMeetingID, name, link);
                zoomMeeting.setCreateTime(resultSet.getLong("createTime"));
                zoomMeeting.setUpdateTime(resultSet.getLong("updateTime"));
                DBConnection.closeDB(conn, stat, resultSet);
                return zoomMeeting;
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ZoomMeeting> select(List<String> zoomMeetingIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from ZoomMeeting where zoomMeetingID = ?";
        List<ZoomMeeting> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String zoomMeetingID : zoomMeetingIDs) {
                stat.setString(1, zoomMeetingID);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    String link = resultSet.getString("link");
                    ZoomMeeting zoomMeeting = new ZoomMeeting(zoomMeetingID, name, link);
                    zoomMeeting.setCreateTime(resultSet.getLong("createTime"));
                    zoomMeeting.setUpdateTime(resultSet.getLong("updateTime"));
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
                String zoomMeetingID = resultSet.getString("zoomMeetingID");
                String name = resultSet.getString("name");
                String link = resultSet.getString("link");
                ZoomMeeting zoomMeeting = new ZoomMeeting(zoomMeetingID, name, link);
                zoomMeeting.setCreateTime(resultSet.getLong("createTime"));
                zoomMeeting.setUpdateTime(resultSet.getLong("updateTime"));
                res.add(zoomMeeting);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
