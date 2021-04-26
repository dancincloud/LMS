package DBUtil;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Ethan Zhang
 * @description : Database utilities for Course class
 * @createTime : [2021/4/25 0:04]
 */
public class CourseDBUtil {

    public static void add(Course course) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Course (`name`, `aveGrade`, `zoomMeetingDirectory`, `recordDirectory`, `assignmentDirectory`, `fileDirectory`) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, course.getName());
            stat.setString(2, course.getCourseID());
            stat.setString(3, DBUtil.changeZoomMeetingDirectoryToString(course.getZoomMeetingDirectory().getZoomMeetingList()));
            stat.setString(4, DBUtil.changeRecordDirectoryToString(course.getRecordDirectory().getRecordList()));
            stat.setString(5, DBUtil.changeAssignmentDirectoryToString(course.getAssignmentDirectory().getAssignmentList()));
            stat.setString(6, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getFileList()));

            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(List<Course> courses) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Course (`name`, `aveGrade`, `zoomMeetingDirectory`, `recordDirectory`, `assignmentDirectory`, `fileDirectory`) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            for (Course course : courses) {
                stat.setString(1, course.getName());
                stat.setString(2, course.getCourseID());
                stat.setString(3, DBUtil.changeZoomMeetingDirectoryToString(course.getZoomMeetingDirectory().getZoomMeetingList()));
                stat.setString(4, DBUtil.changeRecordDirectoryToString(course.getRecordDirectory().getRecordList()));
                stat.setString(5, DBUtil.changeAssignmentDirectoryToString(course.getAssignmentDirectory().getAssignmentList()));
                stat.setString(6, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getFileList()));
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String courseID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Course where courseID = ?";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, courseID);
            stat.executeUpdate();

            Course underDeletionCourse = select(courseID);
            // update instructors
            List<Instructor> instructors = InstructorDBUtil.selectAll();
            for (Instructor instructor : instructors) {
                List<Course> courses = instructor.getCoursedirectory().getCourseList();
                courses.remove(underDeletionCourse);
                InstructorDBUtil.update(instructor);
            }

            // update students
            List<Student> students = StudentDBUtil.selectAll();
            for (Student student : students) {
                List<Course> courses = student.getCoursedirectory().getCourseList();
                courses.remove(underDeletionCourse);
                StudentDBUtil.update(student);
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(List<String> courseIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Course where name = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (String courseID : courseIDs) {
                stat.setString(1, courseID);
                stat.executeUpdate();

                Course underDeletionCourse = select(courseID);
                // update instructors
                List<Instructor> instructors = InstructorDBUtil.selectAll();
                for (Instructor instructor : instructors) {
                    List<Course> courses = instructor.getCoursedirectory().getCourseList();
                    courses.remove(underDeletionCourse);
                    InstructorDBUtil.update(instructor);
                }

                // update students
                List<Student> students = StudentDBUtil.selectAll();
                for (Student student : students) {
                    List<Course> courses = student.getCoursedirectory().getCourseList();
                    courses.remove(underDeletionCourse);
                    StudentDBUtil.update(student);
                }
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Course";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Course course) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Course set name = ?, zoomMeetingDirectory = ?, recordDirectory = ?, assignmentDirectory = ?, fileDirectory = ? where courseID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, course.getName());
            stat.setString(2, DBUtil.changeZoomMeetingDirectoryToString(course.getZoomMeetingDirectory().getZoomMeetingList()));
            stat.setString(3, DBUtil.changeRecordDirectoryToString(course.getRecordDirectory().getRecordList()));
            stat.setString(4, DBUtil.changeAssignmentDirectoryToString(course.getAssignmentDirectory().getAssignmentList()));
            stat.setString(5, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getFileList()));
            stat.setString(6, course.getCourseID());
            stat.executeUpdate();
            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(List<Course> courses) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Course set name = ?, zoomMeetingDirectory = ?, recordDirectory = ?, assignmentDirectory = ?, fileDirectory = ? where courseID = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            for (Course course : courses) {
                stat.setString(1, course.getName());
                stat.setString(2, DBUtil.changeZoomMeetingDirectoryToString(course.getZoomMeetingDirectory().getZoomMeetingList()));
                stat.setString(3, DBUtil.changeRecordDirectoryToString(course.getRecordDirectory().getRecordList()));
                stat.setString(4, DBUtil.changeAssignmentDirectoryToString(course.getAssignmentDirectory().getAssignmentList()));
                stat.setString(5, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getFileList()));
                stat.setString(6, course.getCourseID());
                stat.executeUpdate();
            }

            DBConnection.closeDB(conn, stat, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Course select(String courseID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course where courseID = ?";
        Course course = new Course();

        try {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, courseID);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                course.setName(resultSet.getString("name"));
                course.setCourseID(resultSet.getString("courseID"));

                ZoomMeetingDirectory zoomMeetingDirectory = new ZoomMeetingDirectory();
                zoomMeetingDirectory.setZoomMeetingList(ZoomMeetingDBUtil.select(Arrays.asList(resultSet.getString("zoomMeetingDirectory").split("\\|"))));
                course.setZoomMeetingDirectory(zoomMeetingDirectory);

                RecordDirectory recordDirectory = new RecordDirectory();
                recordDirectory.setRecordList(RecordDBUtil.select(Arrays.asList(resultSet.getString("recordDirectory").split("\\|"))));
                course.setRecordDirectory(recordDirectory);

                AssignmentDirectory assignmentDirectory = new AssignmentDirectory();
                assignmentDirectory.setAssignmentList(AssignmentDBUtil.select(Arrays.asList(resultSet.getString("assignmentDirectory").split("\\|"))));
                course.setAssignmentDirectory(assignmentDirectory);

                FileDirectory fileDirectory = new FileDirectory();
                fileDirectory.setFileList(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                course.setFileDirectory(fileDirectory);
            }

            DBConnection.closeDB(conn, stat, resultSet);
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public static List<Course> select(List<String> courseIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course where name = ?";
        List<Course> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = null;
            for (String courseID : courseIDs) {
                Course course = new Course();
                course.setCourseID(courseID);

                stat.setString(1, courseID);
                resultSet = stat.executeQuery();

                while (resultSet.next()){
                    course.setName(resultSet.getString("name"));
                    course.setCourseID(resultSet.getString("courseID"));

                    ZoomMeetingDirectory zoomMeetingDirectory = new ZoomMeetingDirectory();
                    zoomMeetingDirectory.setZoomMeetingList(ZoomMeetingDBUtil.select(Arrays.asList(resultSet.getString("zoomMeetingDirectory").split("\\|"))));
                    course.setZoomMeetingDirectory(zoomMeetingDirectory);

                    RecordDirectory recordDirectory = new RecordDirectory();
                    recordDirectory.setRecordList(RecordDBUtil.select(Arrays.asList(resultSet.getString("recordDirectory").split("\\|"))));
                    course.setRecordDirectory(recordDirectory);

                    AssignmentDirectory assignmentDirectory = new AssignmentDirectory();
                    assignmentDirectory.setAssignmentList(AssignmentDBUtil.select(Arrays.asList(resultSet.getString("assignmentDirectory").split("\\|"))));
                    course.setAssignmentDirectory(assignmentDirectory);

                    FileDirectory fileDirectory = new FileDirectory();
                    fileDirectory.setFileList(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                    course.setFileDirectory(fileDirectory);

                    res.add(course);
                }
            }
            DBConnection.closeDB(conn, stat, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static List<Course> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course";
        List<Course> res = new ArrayList<>();

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet resultSet = stat.executeQuery();

            while (resultSet.next()){
                Course course = new Course();
                course.setName(resultSet.getString("name"));
                course.setCourseID(resultSet.getString("courseID"));

                ZoomMeetingDirectory zoomMeetingDirectory = new ZoomMeetingDirectory();
                zoomMeetingDirectory.setZoomMeetingList(ZoomMeetingDBUtil.select(Arrays.asList(resultSet.getString("zoomMeetingDirectory").split("\\|"))));
                course.setZoomMeetingDirectory(zoomMeetingDirectory);

                RecordDirectory recordDirectory = new RecordDirectory();
                recordDirectory.setRecordList(RecordDBUtil.select(Arrays.asList(resultSet.getString("recordDirectory").split("\\|"))));
                course.setRecordDirectory(recordDirectory);

                AssignmentDirectory assignmentDirectory = new AssignmentDirectory();
                assignmentDirectory.setAssignmentList(AssignmentDBUtil.select(Arrays.asList(resultSet.getString("assignmentDirectory").split("\\|"))));
                course.setAssignmentDirectory(assignmentDirectory);

                FileDirectory fileDirectory = new FileDirectory();
                fileDirectory.setFileList(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                course.setFileDirectory(fileDirectory);

                res.add(course);
            }

            DBConnection.closeDB(conn, stat, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
