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
        List<String> names = new ArrayList<>();
        for (Assignment assignment : assignments) {
            names.add(assignment.getName());
        }

        return mergeFields(names);
    }

    public static String changeCourseDirectoryToString(List<Course> courses) {
        List<String> courseIDs = new ArrayList<>();
        for (Course course : courses) {
            courseIDs.add(course.getCourseID());
        }

        return mergeFields(courseIDs);
    }

    public static String changeFileDirectoryToString(List<File> files) {
        List<String> names = new ArrayList<>();
        for (File file : files) {
            names.add(file.getName());
        }

        return mergeFields(names);
    }

    public static String changeRecordDirectoryToString(List<Record> records) {
        List<String> names = new ArrayList<>();
        for (Record record : records) {
            names.add(record.getName());
        }

        return mergeFields(names);
    }

    public static String changeZoomMeetingDirectoryToString(List<ZoomMeeting> zoomMeetings) {
        List<String> names = new ArrayList<>();
        for (ZoomMeeting zoomMeeting : zoomMeetings) {
            names.add(zoomMeeting.getName());
        }

        return mergeFields(names);
    }

    public static String mergeFields(List<String> fields) {
        StringJoiner joinFileds = new StringJoiner("|");
        for (String field : fields) {
            joinFileds.add(field);
        }

        return joinFileds.toString();
    }
}
