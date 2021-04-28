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

        List<String> names = new ArrayList<>();
        for (Assignment assignment : assignments) {
            names.add(assignment.getName());
        }

        return mergeFields(names);
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

        List<String> names = new ArrayList<>();
        for (File file : files) {
            names.add(file.getName());
        }

        return mergeFields(names);
    }

    public static String changeRecordDirectoryToString(List<model.Record> records) {

        if (records == null) {
            return null;
        }

        List<String> names = new ArrayList<>();
        for (model.Record record : records) {
            names.add(record.getName());
        }

        return mergeFields(names);
    }

    public static String changeZoomMeetingDirectoryToString(List<ZoomMeeting> zoomMeetings) {

        if (zoomMeetings == null) {
            return null;
        }

        List<String> names = new ArrayList<>();
        for (ZoomMeeting zoomMeeting : zoomMeetings) {
            names.add(zoomMeeting.getName());
        }

        return mergeFields(names);
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
