package model;

/**
 *
 * @author Ke
 */
public class Course {
    private String courseID;
    private String name;
    private String instructorID;
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
        this.courseID = MockDataGenerator.generateID();
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

    public StudentDirectory getStudentDirectory() {
        return studentDirectory;
    }

    public void setStudentDirectory(StudentDirectory studentDirectory) {
        this.studentDirectory = studentDirectory;
    }

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
}
