package DBUtil;

import model.CourseDirectory;
import model.Instructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Ethan Zhang
 * @description : Database utilities for Instructor class
 * @createTime : [2021/4/26 8:45]
 */
public class InstructorDBUtil {

    public static boolean add(Instructor instructor) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Instructor (`instructorID`, `name`, `email`, `username`, `password`, " +
                " `courseIDs`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, instructor.getId());
            stat.setString(2, instructor.getName());
            stat.setString(3, instructor.getEmail());
            stat.setString(4, instructor.getUsername());
            stat.setString(5, instructor.getPassword());
            stat.setString(6, DBUtil.mergeFields(instructor.getCourseIDs()));
            stat.setLong(7, System.currentTimeMillis());
            stat.setLong(8, -1L);

            stat.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean add(List<Instructor> instructors) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Instructor (`instructorID`, `name`, `email`, `username`, `password`, " +
                " `courseIDs`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (Instructor instructor : instructors) {
                stat.setString(1, instructor.getId());
                stat.setString(2, instructor.getName());
                stat.setString(3, instructor.getEmail());
                stat.setString(4, instructor.getUsername());
                stat.setString(5, instructor.getPassword());
                stat.setString(6, DBUtil.mergeFields(instructor.getCourseIDs()));
                stat.setLong(7, System.currentTimeMillis());
                stat.setLong(8, -1L);

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

    public static boolean delete(String instructorID) {
        Instructor instructor = select(instructorID);
        if (instructor == null) {
            return false;
        }
        if (instructor.getCoursedirectory() != null) {
            return false;
        }

        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Instructor where instructorID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, instructorID);
            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean delete(List<String> instructorIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Instructor where instructorID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (String instructorID : instructorIDs) {
                Instructor instructor = select(instructorID);
                if (instructor == null) {
                    System.out.println("Failed to delete null instructors!");
                    continue;
                }
                if (instructor.getCoursedirectory() != null) {
                    System.out.println("Instructor: " + instructorID
                            + ", fail to delete this instructor because he/she still has courses.");
                    continue;
                }

                stat.setString(1, instructorID);
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

    public static boolean update(Instructor instructor) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Instructor set name = ?, email = ?, username = ?, password = ?, courseIDs = ?, " +
                " updateTime = ? where instructorID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, instructor.getName());
            stat.setString(2, instructor.getEmail());
            stat.setString(3, instructor.getUsername());
            stat.setString(4, instructor.getPassword());
            stat.setString(5, DBUtil.mergeFields(instructor.getCourseIDs()));
            stat.setLong(6, System.currentTimeMillis());
            stat.setString(7, instructor.getId());
            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(List<Instructor> instructors) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Instructor set name = ?, email = ?, username = ?, password = ?, courseIDs = ?, " +
                " updateTime = ? where instructorID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (Instructor instructor : instructors) {
                stat.setString(1, instructor.getName());
                stat.setString(2, instructor.getEmail());
                stat.setString(3, instructor.getUsername());
                stat.setString(4, instructor.getPassword());
                stat.setString(5, DBUtil.mergeFields(instructor.getCourseIDs()));
                stat.setLong(6, System.currentTimeMillis());
                stat.setString(7, instructor.getId());
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

    public static Instructor select(String instructorID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Instructor where instructorID = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, instructorID);
            resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Instructor instructor = new Instructor(instructorID, name, email, username, password);

                instructor.setCourseIDs(Arrays.asList(resultSet.getString("courseIDs").split("\\|")));

                instructor.setCreateTime(resultSet.getLong("createTime"));
                instructor.setUpdateTime(resultSet.getLong("updateTime"));

                return instructor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return null;
    }

    public static Instructor selectByUsername(String username) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Instructor where username = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, username);
            resultSet = stat.executeQuery();

            if (resultSet.next()){
                String instructorID = resultSet.getString("instructorID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Instructor instructor = new Instructor(instructorID, name, email, username, password);

                instructor.setCourseIDs(Arrays.asList(resultSet.getString("courseIDs").split("\\|")));

                instructor.setCreateTime(resultSet.getLong("createTime"));
                instructor.setUpdateTime(resultSet.getLong("updateTime"));

                return instructor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return null;
    }

    public static List<Instructor> select(List<String> instructorIDs) {

        if (instructorIDs == null) {
            return new ArrayList<>();
        }

        if (instructorIDs.size() == 0) {
            return new ArrayList<>();
        }

        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Instructor where instructorID = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<Instructor> res = new ArrayList<>();

        try {
            stat = conn.prepareStatement(sql);
            resultSet = null;
            for (String instructorID : instructorIDs) {
                stat.setString(1, instructorID);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Instructor instructor = new Instructor(instructorID, name, email, username, password);

                    instructor.setCourseIDs(Arrays.asList(resultSet.getString("courseIDs").split("\\|")));

                    instructor.setCreateTime(resultSet.getLong("createTime"));
                    instructor.setUpdateTime(resultSet.getLong("updateTime"));

                    res.add(instructor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return res;
    }

    public static List<Instructor> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Instructor";
        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<Instructor> res = new ArrayList<>();

        try {
            stat = conn.prepareStatement(sql);
            resultSet = stat.executeQuery();

            while (resultSet.next()){
                String instructorID = resultSet.getString("instructorID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Instructor instructor = new Instructor(instructorID, name, email, username, password);

                instructor.setCourseIDs(Arrays.asList(resultSet.getString("courseIDs").split("\\|")));

                instructor.setCreateTime(resultSet.getLong("createTime"));
                instructor.setUpdateTime(resultSet.getLong("updateTime"));

                res.add(instructor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return res;
    }

}
