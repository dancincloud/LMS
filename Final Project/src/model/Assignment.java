package model;

/**
 *
 * @author Ke
 */
public class Assignment {
    public String getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(String assignmentID) {
        this.assignmentID = assignmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AssignmentType getType() {
        return type;
    }

    public void setType(AssignmentType type) {
        this.type = type;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
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

    public enum AssignmentType{
        PROJECT("Project"),QUIZ("Quiz"),EXAM("Exam");
        
        private String value;
        
        private AssignmentType(String value){
            this.value=value;
        }
        public String getValue() {
            return value;
        }
        @Override
        public String toString(){
            return value;
        }
    }

    private String assignmentID;
    private String name;
    private String content;
    private AssignmentType type;
    private double grade;
    private long createTime;
    private long updateTime;

    public Assignment(String assignmentID, String name, String content, AssignmentType type) {
        this.assignmentID = assignmentID;
        this.name = name;
        this.content = content;
        this.type = type;
    }


    
}
