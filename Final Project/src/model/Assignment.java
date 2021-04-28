package model;

/**
 *
 * @author Ke
 */
public class Assignment {
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
    private String name;
    private String content;
    private double aveGrade;
    private AssignmentType type;

    public Assignment(String name, String content, AssignmentType type) {
        this.name = name;
        this.content = content;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAveGrade() {
        return aveGrade;
    }

    public void setAveGrade(double aveGrade) {
        this.aveGrade = aveGrade;
    }
    
    public void setType(AssignmentType type){
        this.type = type;
    }
    
    public AssignmentType getType(){
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
