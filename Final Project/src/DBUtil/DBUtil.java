package DBUtil;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author : Ethan Zhang
 * @description : utilities for database
 * @createTime : [2021/4/26 7:32]
 */
public class DBUtil {
    public static String changeAssignmentDirectoryToString(List<Assignment> assignments) {

        if (assignments == null) {
            return null;
        }

        List<String> assignmentIDs = new ArrayList<>();
        for (Assignment assignment : assignments) {
            assignmentIDs.add(assignment.getAssignmentID());
        }

        return mergeFields(assignmentIDs);
    }

    public static String changeCourseDirectoryToString(List<Course> courses) {

        if (courses == null) {
            return null;
        }

        List<String> courseIDs = new ArrayList<>();
        for (Course course : courses) {
            courseIDs.add(course.getCourseID());
        }

        return mergeFields(courseIDs);
    }

    public static String changeFileDirectoryToString(List<File> files) {

        if (files == null) {
            return null;
        }

        List<String> fileIDs = new ArrayList<>();
        for (File file : files) {
            fileIDs.add(file.getFileID());
        }

        return mergeFields(fileIDs);
    }

    public static String changeRecordDirectoryToString(List<Record> records) {

        if (records == null) {
            return null;
        }

        List<String> recordIDs = new ArrayList<>();
        for (Record record : records) {
            recordIDs.add(record.getRecordID());
        }

        return mergeFields(recordIDs);
    }

    public static String changeZoomMeetingDirectoryToString(List<ZoomMeeting> zoomMeetings) {

        if (zoomMeetings == null) {
            return null;
        }

        List<String> zoomMeetingIDs = new ArrayList<>();
        for (ZoomMeeting zoomMeeting : zoomMeetings) {
            zoomMeetingIDs.add(zoomMeeting.getZoomMeetingID());
        }

        return mergeFields(zoomMeetingIDs);
    }

    public static String changeStudentDirectoryToString(List<Student> students) {

        if (students == null) {
            return null;
        }

        List<String> studentIDs = new ArrayList<>();
        for (Student student : students) {
            studentIDs.add(student.getId());
        }

        return mergeFields(studentIDs);
    }

    public static String mergeFields(List<String> fields) {
        StringJoiner joinFileds = new StringJoiner("|");
        for (String field : fields) {
            joinFileds.add(field);
        }

        return joinFileds.toString();
    }
}
