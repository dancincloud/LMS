package model;

import DBUtil.StudentDBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ke
 */
public class Course {
    private String courseID;
    private String name;
    private String instructorID;
    private List<String> studentIDs;
    private StudentDirectory studentDirectory;
    private ZoomMeetingDirectory zoomMeetingDirectory;
    private RecordDirectory recordDirectory;
    private AssignmentDirectory assignmentDirectory;
    private FileDirectory fileDirectory;
    private long createTime;
    private long updateTime;

    public Course(String courseID, String name, String instructorID) {
        this.courseID = courseID;
        this.name = name;
        this.instructorID = instructorID;
    }

    public Course(String name, String instructorID) {
        this.courseID = DataGenerator.generateID();
        this.name = name;
        this.instructorID = instructorID;
    }

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

    @Override
    public String toString(){
        return String.format("id = %10s, name = %10s", courseID, name);
    }

    public ZoomMeetingDirectory getZoomMeetingDirectory() {
        if(zoomMeetingDirectory == null) zoomMeetingDirectory = new ZoomMeetingDirectory();
        return zoomMeetingDirectory;
    }

    public void setZoomMeetingDirectory(ZoomMeetingDirectory zoomMeetingDirectory) {
        this.zoomMeetingDirectory = zoomMeetingDirectory;
    }

    public RecordDirectory getRecordDirectory() {
        if(recordDirectory == null) recordDirectory = new RecordDirectory();
        return recordDirectory;
    }

    public void setRecordDirectory(RecordDirectory recordDirectory) {
        this.recordDirectory = recordDirectory;
    }

    public AssignmentDirectory getAssignmentDirectory() {
        if(assignmentDirectory == null) assignmentDirectory = new AssignmentDirectory();
        return assignmentDirectory;
    }

    public void setAssignmentDirectory(AssignmentDirectory assignmentDirectory) {
        this.assignmentDirectory = assignmentDirectory;
    }

    public FileDirectory getFileDirectory() {
        if(fileDirectory == null) fileDirectory = new FileDirectory();

        return fileDirectory;
    }

    public void setFileDirectory(FileDirectory fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public StudentDirectory getStudentDirectory() {
        return new StudentDirectory(StudentDBUtil.select(studentIDs));
//        if(studentDirectory == null) studentDirectory = new StudentDirectory();
//        return studentDirectory;
    }

//    public void setStudentDirectory(StudentDirectory studentDirectory) {
//        this.studentDirectory = studentDirectory;
//    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getStudentIDs() {
        if(studentIDs == null) studentIDs = new ArrayList<>();

        return studentIDs;
    }

    public void setStudentIDs(List<String> studentIDs) {
        this.studentIDs = studentIDs;
    }
}
