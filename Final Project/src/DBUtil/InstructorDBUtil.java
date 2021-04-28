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

    public static void add(Instructor instructor) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Instructor (`name`, `instructorID`, `email`, `courseDirectory`) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, instructor.getName());
            stat.setString(2, instructor.getId());
            stat.setString(3, instructor.getEmail());

            if (instructor.getCoursedirectory() != null) {
                stat.setString(4, DBUtil.changeCourseDirectoryToString(instructor.getCoursedirectory().getCourseList()));
            } else {
                stat.setString(4, null);
            }

            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<Instructor> instructors) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Instructor (`name`, `instructorID`, `email`, `courseDirectory`) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (Instructor instructor : instructors) {
                stat.setString(1, instructor.getName());
                stat.setString(2, instructor.getId());
                stat.setString(3, instructor.getEmail());

                if (instructor.getCoursedirectory() != null) {
                    stat.setString(4, DBUtil.changeCourseDirectoryToString(instructor.getCoursedirectory().getCourseList()));
                } else {
                    stat.setString(4, null);
                }

                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String instructorID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Instructor where instructorID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, instructorID);
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(List<String> instructorIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Instructor where instructorID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (String instructorID : instructorIDs) {
                stat.setString(1, instructorID);
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Instructor";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Instructor instructor) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Instructor set name = ?, email = ?, courseDirectory = ? where instructorID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, instructor.getName());
            stat.setString(2, instructor.getEmail());

            if (instructor.getCoursedirectory() != null) {
                stat.setString(3, DBUtil.changeCourseDirectoryToString(instructor.getCoursedirectory().getCourseList()));
            } else {
                stat.setString(3, null);
            }

            stat.setString(4, instructor.getId());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<Instructor> instructors) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Instructor set name = ?, email = ?, courseDirectory = ? where instructorID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (Instructor instructor : instructors) {
                stat.setString(1, instructor.getName());
                stat.setString(2, instructor.getEmail());

                if (instructor.getCoursedirectory() != null) {
                    stat.setString(3, DBUtil.changeCourseDirectoryToString(instructor.getCoursedirectory().getCourseList()));
                } else {
                    stat.setString(3, null);
                }

                stat.setString(4, instructor.getId());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Instructor select(String instructorID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Instructor where instructorID = ?";

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, instructorID);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()){
                Instructor instructor = new Instructor();
                instructor.setName(resultSet.getString("name"));
                instructor.setId(resultSet.getString("instructorID"));
                instructor.setEmail(resultSet.getString("email"));

                if (resultSet.getString("courseDirectory") != null) {
                    CourseDirectory courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                    instructor.setCoursedirectory(courseDirectory);
                }

                DBConnection.closeDB(conn, stat, resultSet);
                return instructor;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Instructor> select(List<String> instructorIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Instructor where instructorID = ?";
        List<Instructor> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String instructorID : instructorIDs) {


                stat.setString(1, instructorID);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    Instructor instructor = new Instructor();
                    instructor.setName(resultSet.getString("name"));
                    instructor.setId(instructorID);
                    instructor.setEmail(resultSet.getString("email"));

                    if (resultSet.getString("courseDirectory") != null) {
                        CourseDirectory courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                        instructor.setCoursedirectory(courseDirectory);
                    }

                    res.add(instructor);
                }
            }
            DBConnection.closeDB(conn, stat, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static List<Instructor> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Instructor";
        List<Instructor> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                Instructor instructor = new Instructor();
                instructor.setName(resultSet.getString("name"));
                instructor.setId(resultSet.getString("instructorID"));
                instructor.setEmail(resultSet.getString("email"));

                if (resultSet.getString("courseDirectory") != null) {
                    CourseDirectory courseDirectory = new CourseDirectory(CourseDBUtil.select(Arrays.asList(resultSet.getString("courseDirectory").split("\\|"))));
                    instructor.setCoursedirectory(courseDirectory);
                }

                res.add(instructor);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

}
