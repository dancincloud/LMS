package model;

/**
 *
 * @author Ke
 */
public class Assignment {
    enum AssignmentType {
        PROJECT,
        QUIZ,
        EXAM
    }
    
    
    private String name;
    private double aveGrade;
    private AssignmentType type;

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
}
