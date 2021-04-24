package DBUtil;

import model.Assignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 /**
 * @author : Ethan Zhang
 * @description : Database utilities for Assignment class
 * @createTime : [2021/4/25 0:04]
 * @updateTime : [2021/4/25 0:04]
 */
public class AssignmentDBUtil {

    public static void add(Assignment assignment) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO `lmsdb`.`assignment` (`name`, `aveGrade`) VALUES (?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, assignment.getName());
            stat.setDouble(2, assignment.getAveGrade());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<Assignment> assignments) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO `lmsdb`.`assignment` (`name`, `aveGrade`) VALUES (?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (Assignment assignment : assignments) {
                stat.setString(1, assignment.getName());
                stat.setDouble(2, assignment.getAveGrade());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String name) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Assignment where name = ?";
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
        String sql = "delete from Assignment where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (String name : names) {
                stat.setString(1, name);
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Assignment";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Assignment assignment) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Assignment set aveGrade = ? where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setDouble(1, assignment.getAveGrade());
            stat.setString(2, assignment.getName());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<Assignment> assignments) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Assignment set aveGrade = ? where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (Assignment assignment : assignments) {
                stat.setDouble(1, assignment.getAveGrade());
                stat.setString(2, assignment.getName());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Assignment select(String name) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from assignment where name = ?";
        Assignment assignment = new Assignment();

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, name);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                assignment.setName(resultSet.getString("name"));
                assignment.setAveGrade(resultSet.getDouble("aveGrade"));
            }

            DBConnection.closeDB(conn, stat, resultSet);
            return assignment;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignment;
    }

    public static List<Assignment> select(List<String> names) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from assignment where name = ?";
        List<Assignment> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String name : names) {
                Assignment assignment = new Assignment();
                assignment.setName(name);

                stat.setString(1, name);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    assignment.setAveGrade(resultSet.getDouble("aveGrade"));
                    res.add(assignment);
                }
            }
            DBConnection.closeDB(conn, stat, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static List<Assignment> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from assignment";
        List<Assignment> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                Assignment assignment = new Assignment();
                assignment.setName(resultSet.getString("name"));
                assignment.setAveGrade(resultSet.getDouble("aveGrade"));
                res.add(assignment);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void main(String[] args) {
        Assignment assignment1 = new Assignment();
        assignment1.setName("first");
        assignment1.setAveGrade(90.1);

        Assignment assignment2 = new Assignment();
        assignment2.setName("second");
        assignment2.setAveGrade(90.2);

        Assignment assignment3 = new Assignment();
        assignment3.setName("third");
        assignment3.setAveGrade(90.3);

//        add(assignment1);
//
//        List<Assignment> assignments = new ArrayList<>();
//        assignments.add(assignment2);
//        assignments.add(assignment3);
//        add(assignments);

//        delete("first");

//        List<String> names = new ArrayList<>();
//        names.add("second");
//        names.add("third");
//        delete(names);

//        assignment1.setAveGrade(90.4);
//        update(assignment1);

//        assignment2.setAveGrade(90.5);
//        assignment3.setAveGrade(90.6);
//        List<Assignment> assignments = new ArrayList<>();
//        assignments.add(assignment2);
//        assignments.add(assignment3);
//        update(assignments);

//        Assignment a1 = select("first");
//        System.out.println(a1.getName() + " " + a1.getAveGrade());

//        List<String> names = new ArrayList<>();
//        names.add("second");
//        names.add("third");
//        List<Assignment> assignments = select(names);
//        for (Assignment assignment : assignments) {
//            System.out.println(assignment.getName() + " " + assignment.getAveGrade());
//        }

    }

}
