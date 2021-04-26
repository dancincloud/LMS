package model;

/**
 *
 * @author Ke
 */
public class Course {
    private String name;
    private String courseID;
    private ZoomMeetingDirectory zoomMeetingDirectory;
    private RecordDirectory recordDirectory;
    private AssignmentDirectory assignmentDirectory;
    private FileDirectory fileDirectory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public ZoomMeetingDirectory getZoomMeetingDirectory() {
        return zoomMeetingDirectory;
    }

    public void setZoomMeetingDirectory(ZoomMeetingDirectory zoomMeetingDirectory) {
        this.zoomMeetingDirectory = zoomMeetingDirectory;
    }

    public RecordDirectory getRecordDirectory() {
        return recordDirectory;
    }

    public void setRecordDirectory(RecordDirectory recordDirectory) {
        this.recordDirectory = recordDirectory;
    }

    public AssignmentDirectory getAssignmentDirectory() {
        return assignmentDirectory;
    }

    public void setAssignmentDirectory(AssignmentDirectory assignmentDirectory) {
        this.assignmentDirectory = assignmentDirectory;
    }

    public FileDirectory getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(FileDirectory fileDirectory) {
        this.fileDirectory = fileDirectory;
    }
}
