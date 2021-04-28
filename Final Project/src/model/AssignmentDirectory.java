package model;

import java.util.ArrayList;
import java.util.List;
import model.Assignment.AssignmentType;

public class AssignmentDirectory extends Directory<Assignment>{
    private List<Assignment> assignmentList;

    public AssignmentDirectory(){
        super();
    }
    
    public AssignmentDirectory(List<Assignment> assignmentList) {
        super(assignmentList);
    }
    
    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }
    
    public Assignment createAssignment(String name, String content, AssignmentType type){
        Assignment assignment = new Assignment(name, content, type);
        assignmentList = new ArrayList();
        assignmentList.add(assignment);
        return assignment;
    }
    
}