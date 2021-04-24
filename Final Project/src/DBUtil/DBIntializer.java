package DBUtil;

import java.sql.*;

/**
 * @author : Ethan Zhang
 * @description :
 * @createTime : [2021/4/25 0:04]
 * @updateTime : [2021/4/25 0:04]
 */
public class DBIntializer {

    // table creation sql
    static final String ASSIGNMENT_CREATION = "CREATE TABLE `Assignment` (" +
            "  `name` varchar(255) NOT NULL," +
            "  `aveGrade` double(255, 1) NULL," +
            "  PRIMARY KEY (`name`))";

    static final String COURSE_CREATION = "CREATE TABLE `Course` (" +
            "  `name` varchar(255) NOT NULL," +
            "  `courseID` bigint NOT NULL," +
            "  `zoomMeetingDirectory` varchar(1000) NULL," +
            "  `recordDirectory` varchar(1000) NULL," +
            "  `assignmentDirectory` varchar(1000) NULL," +
            "  `fileDirectory` varchar(1000) NULL," +
            "  PRIMARY KEY (`courseID`)" +
            ");";

    static final String FILE_CREATION = "CREATE TABLE `File` (" +
            "  `name` varchar(255) NOT NULL," +
            "  `link` varchar(255) NOT NULL," +
            "  PRIMARY KEY (`name`)" +
            ")";

    static final String INSTRUCTOR_CREATION = "CREATE TABLE `Instructor`  (" +
            "  `name` varchar(255) NOT NULL," +
            "  `instructorID` bigint NOT NULL," +
            "  `email` varchar(255) NULL," +
            "  `courseDirectory` varchar(1000) NULL," +
            "  PRIMARY KEY (`instructorID`)" +
            ");";

    static final String RECORD_CREATION = "CREATE TABLE `Record` (" +
            "  `name` varchar(255) NOT NULL," +
            "  `link` varchar(255) NOT NULL," +
            "  PRIMARY KEY (`name`)" +
            ")";

    static final String STUDENT_CREATION = "CREATE TABLE `Student`  (" +
            "  `studentID` bigint NOT NULL," +
            "  `name` varchar(255) NOT NULL," +
            "  `gpa` double(255, 1) NULL," +
            "  `email` varchar(255) NULL," +
            "  `courseDirectory` varchar(1000) NULL," +
            "  PRIMARY KEY (`studentID`)" +
            ");";

    static final String ZOOM_MEETING_CREATION = "CREATE TABLE `ZoomMeeting` (" +
            "  `name` varchar(255) NOT NULL," +
            "  `link` varchar(255) NOT NULL," +
            "  PRIMARY KEY (`name`)" +
            ")";

    /**
     * @author Ethan Zhang
     * @description initialize the LMS database
     * @createTime  25/04/2021
     */
    public static void initializeDBIfNotExist() {
        try {
            Connection conn = DBConnection.getConnection(DBConnection.DEFAULT_DB_URL);

            // judge whether the database exists
            String checkdatabase="show databases like \"LMSDB\"";

            PreparedStatement stat = conn.prepareStatement(checkdatabase);
            ResultSet resultSet = stat.executeQuery(checkdatabase);

            if (resultSet.next()) {
                System.out.println("LMSDB exist! No need to create.");
                stat.close();
                conn.close();
            }else{
                String createdatabase="create database LMSDB";
                stat.executeUpdate(createdatabase);
                stat.close();
                conn.close();
                System.out.println("Create database success!");
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * @author Ethan Zhang
     * @description initialize tables in LMSDB
     * @createTime  25/04/2021
     */
    public static void initializeTablesIfNotExist() {
        try {
            Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
            PreparedStatement stat = null;

            // create table Assignment
            if (validateTableNameExist("Assignment", conn)) {
                stat = conn.prepareStatement(ASSIGNMENT_CREATION);
                stat.executeUpdate();
            }

            // create table Course
            if (validateTableNameExist("Course", conn)) {
                stat = conn.prepareStatement(COURSE_CREATION);
                stat.executeUpdate();
            }

            // create table File
            if (validateTableNameExist("File", conn)) {
                stat = conn.prepareStatement(FILE_CREATION);
                stat.executeUpdate();
            }

            // create table Instructor
            if (validateTableNameExist("Instructor", conn)) {
                stat = conn.prepareStatement(INSTRUCTOR_CREATION);
                stat.executeUpdate();
            }

            // create table Record
            if (validateTableNameExist("Record", conn)) {
                stat = conn.prepareStatement(RECORD_CREATION);
                stat.executeUpdate();
            }

            // create table Student
            if (validateTableNameExist("Student", conn)) {
                stat = conn.prepareStatement(STUDENT_CREATION);
                stat.executeUpdate();
            }

            // create table ZoomMeeting
            if (validateTableNameExist("ZoomMeeting", conn)) {
                stat = conn.prepareStatement(ZOOM_MEETING_CREATION);
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
            System.out.println("Initiate tables success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateTableNameExist(String tableName, Connection con) {
        ResultSet rs = null;
        boolean res = false;
        try {
            rs = con.getMetaData().getTables(null, null, tableName, null);
            res = rs.next();
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        initializeDBIfNotExist();
        initializeTablesIfNotExist();
    }
}
