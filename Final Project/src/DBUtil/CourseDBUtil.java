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

/**
 * wait to complete
 */
public class CourseDBUtil {

    public static boolean add(Course course) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Course (`courseID`, `name`, `instructorID`, `studentIDs`, " +
                " `zoomMeetingDirectory`, `recordDirectory`, `assignmentDirectory`, `fileDirectory`, " +
                " `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, course.getCourseID());
            stat.setString(2, course.getName());
            stat.setString(3, course.getInstructorID());
            stat.setString(4, DBUtil.mergeFields(course.getStudentIDs()));

            if (course.getZoomMeetingDirectory() != null) {
                stat.setString(5, DBUtil.changeZoomMeetingDirectoryToString(course.getZoomMeetingDirectory().getList()));
            } else {
                stat.setString(5, null);
            }

            if (course.getRecordDirectory() != null) {
                stat.setString(6, DBUtil.changeRecordDirectoryToString(course.getRecordDirectory().getList()));
            } else {
                stat.setString(6, null);
            }

            if (course.getAssignmentDirectory() != null) {
                stat.setString(7, DBUtil.changeAssignmentDirectoryToString(course.getAssignmentDirectory().getList()));
            } else {
                stat.setString(7, null);
            }

            if (course.getFileDirectory() != null) {
                stat.setString(8, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getList()));
            } else {
                stat.setString(8, null);
            }

            stat.setLong(9, System.currentTimeMillis());
            stat.setLong(10, -1L);

            stat.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean add(List<Course> courses) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "INSERT INTO Course (`courseID`, `name`, `instructorID`, `studentIDs`, " +
                " `zoomMeetingDirectory`, `recordDirectory`, `assignmentDirectory`, `fileDirectory`, " +
                " `createTime`, `updateTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (Course course : courses) {
                stat.setString(1, course.getCourseID());
                stat.setString(2, course.getName());
                stat.setString(3, course.getInstructorID());
                stat.setString(4, DBUtil.mergeFields(course.getStudentIDs()));

                if (course.getZoomMeetingDirectory() != null) {
                    stat.setString(5, DBUtil.changeZoomMeetingDirectoryToString(course.getZoomMeetingDirectory().getList()));
                } else {
                    stat.setString(5, null);
                }

                if (course.getRecordDirectory() != null) {
                    stat.setString(6, DBUtil.changeRecordDirectoryToString(course.getRecordDirectory().getList()));
                } else {
                    stat.setString(6, null);
                }

                if (course.getAssignmentDirectory() != null) {
                    stat.setString(7, DBUtil.changeAssignmentDirectoryToString(course.getAssignmentDirectory().getList()));
                } else {
                    stat.setString(7, null);
                }

                if (course.getFileDirectory() != null) {
                    stat.setString(8, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getList()));
                } else {
                    stat.setString(8, null);
                }

                stat.setLong(9, System.currentTimeMillis());
                stat.setLong(10, -1L);

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

    public static boolean delete(String courseID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Course where courseID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, courseID);
            stat.executeUpdate();

            Course underDeletionCourse = select(courseID);
            // update instructors
            List<Instructor> instructors = InstructorDBUtil.selectAll();
            for (Instructor instructor : instructors) {
                if (instructor.getCoursedirectory() != null) {
                    List<Course> courses = instructor.getCoursedirectory().getList();
                    courses.remove(underDeletionCourse);
                    InstructorDBUtil.update(instructor);
                }
            }

            // update students
            List<Student> students = StudentDBUtil.selectAll();
            for (Student student : students) {
                student.getCourseIDs().remove(courseID);
            }
            StudentDBUtil.update(students);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean delete(List<String> courseIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "delete from Course where courseID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            for (String courseID : courseIDs) {
                stat.setString(1, courseID);
                stat.executeUpdate();

                Course underDeletionCourse = select(courseID);
                // update instructors
                List<Instructor> instructors = InstructorDBUtil.selectAll();
                for (Instructor instructor : instructors) {
                    if (instructor.getCoursedirectory() != null) {
                        List<Course> courses = instructor.getCoursedirectory().getList();
                        courses.remove(underDeletionCourse);
                        InstructorDBUtil.update(instructor);
                    }
                }

                // update students
                List<Student> students = StudentDBUtil.selectAll();
                for (Student student : students) {
                    student.getCourseIDs().remove(courseID);
                }
                StudentDBUtil.update(students);
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
        String sql = "delete from Course";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.executeUpdate();

            // update instructors
            List<Instructor> instructors = InstructorDBUtil.selectAll();
            for (Instructor instructor : instructors) {
                instructor.setCoursedirectory(null);
            }
            InstructorDBUtil.update(instructors);

            // update students
            List<Student> students = StudentDBUtil.selectAll();
            for (Student student : students) {
                student.setCourseIDs(null);
            }
            StudentDBUtil.update(students);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(Course course) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Course set name = ?, instructorID = ?, studentIDs = ?, zoomMeetingDirectory = ?, " +
                " recordDirectory = ?, assignmentDirectory = ?, fileDirectory = ?, updateTime = ? where courseID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, course.getName());
            stat.setString(2, course.getInstructorID());
            stat.setString(3, DBUtil.mergeFields(course.getStudentIDs()));

            if (course.getZoomMeetingDirectory() != null) {
                stat.setString(4, DBUtil.changeZoomMeetingDirectoryToString(course.getZoomMeetingDirectory().getList()));
            } else {
                stat.setString(4, null);
            }

            if (course.getRecordDirectory() != null) {
                stat.setString(5, DBUtil.changeRecordDirectoryToString(course.getRecordDirectory().getList()));
            } else {
                stat.setString(5, null);
            }

            if (course.getAssignmentDirectory() != null) {
                stat.setString(6, DBUtil.changeAssignmentDirectoryToString(course.getAssignmentDirectory().getList()));
            } else {
                stat.setString(6, null);
            }

            if (course.getFileDirectory() != null) {
                stat.setString(7, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getList()));
            } else {
                stat.setString(7, null);
            }

            stat.setLong(8, System.currentTimeMillis());
            stat.setString(9, course.getCourseID());

            stat.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeDB(conn, stat, null);
        }
    }

    public static boolean update(List<Course> courses) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "update Course set name = ?, instructorID = ?, studentIDs = ?, zoomMeetingDirectory = ?, " +
                " recordDirectory = ?, assignmentDirectory = ?, fileDirectory = ?, updateTime = ? where courseID = ?";
        PreparedStatement stat = null;

        try {
            stat = conn.prepareStatement(sql);

            for (Course course : courses) {
                stat.setString(1, course.getName());
                stat.setString(2, course.getInstructorID());
                stat.setString(3, DBUtil.mergeFields(course.getStudentIDs()));

                if (course.getZoomMeetingDirectory() != null) {
                    stat.setString(4, DBUtil.changeZoomMeetingDirectoryToString(course.getZoomMeetingDirectory().getList()));
                } else {
                    stat.setString(4, null);
                }

                if (course.getRecordDirectory() != null) {
                    stat.setString(5, DBUtil.changeRecordDirectoryToString(course.getRecordDirectory().getList()));
                } else {
                    stat.setString(5, null);
                }

                if (course.getAssignmentDirectory() != null) {
                    stat.setString(6, DBUtil.changeAssignmentDirectoryToString(course.getAssignmentDirectory().getList()));
                } else {
                    stat.setString(6, null);
                }

                if (course.getFileDirectory() != null) {
                    stat.setString(7, DBUtil.changeFileDirectoryToString(course.getFileDirectory().getList()));
                } else {
                    stat.setString(7, null);
                }

                stat.setLong(8, System.currentTimeMillis());
                stat.setString(9, course.getCourseID());

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

    public static Course select(String courseID) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course where courseID = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;

        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, courseID);
            resultSet = stat.executeQuery();

            if (resultSet.next()){
                String name = resultSet.getString("name");
                String instructorID = resultSet.getString("instructorID");
                Course course = new Course(courseID, name, instructorID);

                course.setStudentIDs(Arrays.asList(resultSet.getString("studentIDs").split("\\|")));

                if (resultSet.getString("zoomMeetingDirectory") != null) {
                    ZoomMeetingDirectory zoomMeetingDirectory = new ZoomMeetingDirectory(ZoomMeetingDBUtil.select(Arrays.asList(resultSet.getString("zoomMeetingDirectory").split("\\|"))));
                    course.setZoomMeetingDirectory(zoomMeetingDirectory);
                }

                if (resultSet.getString("recordDirectory") != null) {
                    RecordDirectory recordDirectory = new RecordDirectory(RecordDBUtil.select(Arrays.asList(resultSet.getString("recordDirectory").split("\\|"))));
                    course.setRecordDirectory(recordDirectory);
                }

                if (resultSet.getString("assignmentDirectory") != null) {
                    AssignmentDirectory assignmentDirectory = new AssignmentDirectory(AssignmentDBUtil.select(Arrays.asList(resultSet.getString("assignmentDirectory").split("\\|"))));
                    course.setAssignmentDirectory(assignmentDirectory);
                }

                if (resultSet.getString("fileDirectory") != null) {
                    FileDirectory fileDirectory = new FileDirectory(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                    course.setFileDirectory(fileDirectory);
                }

                course.setCreateTime(resultSet.getLong("createTime"));
                course.setUpdateTime(resultSet.getLong("updateTime"));
                DBConnection.closeDB(conn, stat, resultSet);
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return null;
    }

    public static List<Course> select(List<String> courseIDs) {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course where courseID = ?";
        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<Course> res = new ArrayList<>();

        try {
            stat = conn.prepareStatement(sql);
            resultSet = null;
            for (String courseID : courseIDs) {
                stat.setString(1, courseID);
                resultSet = stat.executeQuery();

                if (resultSet.next()){
                    String name = resultSet.getString("name");
                    String instructorID = resultSet.getString("instructorID");
                    Course course = new Course(courseID, name, instructorID);

                    if(resultSet.getString("studentIDs") != null){
                        course.setStudentIDs(Arrays.asList(resultSet.getString("studentIDs").split("\\|")));
                    }

                    if (resultSet.getString("zoomMeetingDirectory") != null) {
                        ZoomMeetingDirectory zoomMeetingDirectory = new ZoomMeetingDirectory(ZoomMeetingDBUtil.select(Arrays.asList(resultSet.getString("zoomMeetingDirectory").split("\\|"))));
                        course.setZoomMeetingDirectory(zoomMeetingDirectory);
                    }

                    if (resultSet.getString("recordDirectory") != null) {
                        RecordDirectory recordDirectory = new RecordDirectory(RecordDBUtil.select(Arrays.asList(resultSet.getString("recordDirectory").split("\\|"))));
                        course.setRecordDirectory(recordDirectory);
                    }

                    if (resultSet.getString("assignmentDirectory") != null) {
                        AssignmentDirectory assignmentDirectory = new AssignmentDirectory(AssignmentDBUtil.select(Arrays.asList(resultSet.getString("assignmentDirectory").split("\\|"))));
                        course.setAssignmentDirectory(assignmentDirectory);
                    }

                    if (resultSet.getString("fileDirectory") != null) {
                        FileDirectory fileDirectory = new FileDirectory(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                        course.setFileDirectory(fileDirectory);
                    }

                    course.setCreateTime(resultSet.getLong("createTime"));
                    course.setUpdateTime(resultSet.getLong("updateTime"));

                    res.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return res;
    }

    public static List<Course> selectAll() {
        Connection conn = DBConnection.getConnection(DBConnection.DB_URL);
        String sql = "SELECT * from Course";
        PreparedStatement stat = null;
        ResultSet resultSet = null;
        List<Course> res = new ArrayList<>();

        try {
            stat = conn.prepareStatement(sql);
            resultSet = stat.executeQuery();

            while (resultSet.next()){
                String courseID = resultSet.getString("courseID");
                String name = resultSet.getString("name");
                String instructorID = resultSet.getString("instructorID");
                Course course = new Course(courseID, name, instructorID);

                course.setStudentIDs(Arrays.asList(resultSet.getString("studentIDs").split("\\|")));

                if (resultSet.getString("zoomMeetingDirectory") != null) {
                    ZoomMeetingDirectory zoomMeetingDirectory = new ZoomMeetingDirectory(ZoomMeetingDBUtil.select(Arrays.asList(resultSet.getString("zoomMeetingDirectory").split("\\|"))));
                    course.setZoomMeetingDirectory(zoomMeetingDirectory);
                }

                if (resultSet.getString("recordDirectory") != null) {
                    RecordDirectory recordDirectory = new RecordDirectory(RecordDBUtil.select(Arrays.asList(resultSet.getString("recordDirectory").split("\\|"))));
                    course.setRecordDirectory(recordDirectory);
                }

                if (resultSet.getString("assignmentDirectory") != null) {
                    AssignmentDirectory assignmentDirectory = new AssignmentDirectory(AssignmentDBUtil.select(Arrays.asList(resultSet.getString("assignmentDirectory").split("\\|"))));
                    course.setAssignmentDirectory(assignmentDirectory);
                }

                if (resultSet.getString("fileDirectory") != null) {
                    FileDirectory fileDirectory = new FileDirectory(FileDBUtil.select(Arrays.asList(resultSet.getString("fileDirectory").split("\\|"))));
                    course.setFileDirectory(fileDirectory);
                }

                course.setCreateTime(resultSet.getLong("createTime"));
                course.setUpdateTime(resultSet.getLong("updateTime"));

                res.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeDB(conn, stat, resultSet);
        }

        return res;
    }

}
