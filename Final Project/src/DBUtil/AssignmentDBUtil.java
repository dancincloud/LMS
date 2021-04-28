package DBUtil;

import model.Assignment;
import model.Course;
import model.Instructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Ethan Zhang
 * @description : Database utilities for Assignment class
 * @createTime : [2021/4/25 0:04]
 */


/**
 * wait to complete
 */
public class AssignmentDBUtil {

    public static boolean add(Assignment assignment) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Assignment (`assignmentID`, `name`, `content`, `type`, `grade`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            stat.setString(1, assignment.getAssignmentID());
            stat.setString(2, assignment.getName());
            stat.setString(3, assignment.getContent());
            stat.setString(4, assignment.getType().toString());
            stat.setDouble(5, assignment.getGrade());
            stat.setLong(6, System.currentTimeMillis());
            stat.setLong(7, -1L);
            stat.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean add(List<Assignment> assignments) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Assignment (`assignmentID`, `name`, `content`, `type`, `grade`, `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(sql);
            for (Assignment assignment : assignments) {
                stat.setString(1, assignment.getAssignmentID());
                stat.setString(2, assignment.getName());
                stat.setString(3, assignment.getContent());
                stat.setString(4, assignment.getType().toString());
                stat.setDouble(5, assignment.getGrade());
                stat.setLong(6, System.currentTimeMillis());
                stat.setLong(7, -1L);
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

    public static boolean delete(String assignmentID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Assignment where assignmentID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, assignmentID);
            stat.executeUpdate();

            // update courses
            Assignment underDeletionAssignment = select(assignmentID);
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                if (course.getAssignmentDirectory() != null) {
                    List<Assignment> assignments = course.getAssignmentDirectory().getList();
                    assignments.remove(underDeletionAssignment);
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

    public static boolean delete(List<String> assignmentIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Assignment where assignmentID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (String assignmentID : assignmentIDs) {
                stat.setString(1, assignmentID);
                stat.executeUpdate();

                // update courses
                Assignment underDeletionAssignment = select(assignmentID);
                List<Course> courses = CourseDBUtil.selectAll();
                for (Course course : courses) {
                    if (course.getAssignmentDirectory() != null) {
                        List<Assignment> assignments = course.getAssignmentDirectory().getList();
                        assignments.remove(underDeletionAssignment);
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
        String sql = "delete from Assignment";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.executeUpdate();

            // update courses
            List<Course> courses = CourseDBUtil.selectAll();
            for (Course course : courses) {
                course.setAssignmentDirectory(null);
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

    public static boolean update(Assignment assignment) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Assignment set name = ?, content = ?, type = ?, grade = ?, updateTime = ? where assignmentID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, assignment.getName());
            stat.setString(2, assignment.getContent());
            stat.setString(3, assignment.getType().toString());
            stat.setDouble(4, assignment.getGrade());
            stat.setLong(5, System.currentTimeMillis());
            stat.setString(6, assignment.getAssignmentID());
            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(List<Assignment> assignments) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Assignment set name = ?, content = ?, type = ?, grade = ?, updateTime = ? where assignmentID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (Assignment assignment : assignments) {
                stat.setString(1, assignment.getName());
                stat.setString(2, assignment.getContent());
                stat.setString(3, assignment.getType().toString());
                stat.setDouble(4, assignment.getGrade());
                stat.setLong(5, System.currentTimeMillis());
                stat.setString(6, assignment.getAssignmentID());
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

    public static Assignment select(String assignmentID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Assignment where assignmentID = ?";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, assignmentID);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String content = resultSet.getString("content");
                Assignment.AssignmentType type = Assignment.AssignmentType.valueOf(resultSet.getString("type"));

                Assignment assignment = new Assignment(assignmentID, name, content, type);

                assignment.setGrade(resultSet.getDouble("grade"));
                assignment.setCreateTime(resultSet.getLong("createTime"));
                assignment.setUpdateTime(resultSet.getLong("updateTime"));
                DBConnection.closeDB(conn, stat, resultSet);
                return assignment;
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Assignment> select(List<String> assignmentIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Assignment where assignmentID = ?";
        List<Assignment> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String assignmentID : assignmentIDs) {
                stat.setString(1, assignmentID);
                resultSet = stat.executeQuery();

                if (resultSet.next()){
                    String name = resultSet.getString("name");
                    String content = resultSet.getString("content");
                    Assignment.AssignmentType type = Assignment.AssignmentType.valueOf(resultSet.getString("type"));

                    Assignment assignment = new Assignment(assignmentID, name, content, type);

                    assignment.setGrade(resultSet.getDouble("grade"));
                    assignment.setCreateTime(resultSet.getLong("createTime"));
                    assignment.setUpdateTime(resultSet.getLong("updateTime"));
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
        String sql = "SELECT * from Assignment";
        List<Assignment> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                String assignmentID = resultSet.getString("assignmentID");
                String name = resultSet.getString("name");
                String content = resultSet.getString("content");
                Assignment.AssignmentType type = Assignment.AssignmentType.valueOf(resultSet.getString("type"));

                Assignment assignment = new Assignment(assignmentID, name, content, type);

                assignment.setGrade(resultSet.getDouble("grade"));
                assignment.setCreateTime(resultSet.getLong("createTime"));
                assignment.setUpdateTime(resultSet.getLong("updateTime"));
                res.add(assignment);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void main(String[] args) {
//        Assignment assignment1 = new Assignment();
//        assignment1.setName("first");
//        assignment1.setAveGrade(90.1);
//
//        Assignment assignment2 = new Assignment();
//        assignment2.setName("second");
//        assignment2.setAveGrade(90.2);
//
//        Assignment assignment3 = new Assignment();
//        assignment3.setName("third");
//        assignment3.setAveGrade(90.3);
//
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
