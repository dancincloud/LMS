package model;

import java.util.ArrayList;
import java.util.List;
import model.Assignment.AssignmentType;

public class AssignmentDirectory extends Directory<Assignment>{

    public AssignmentDirectory(){
        super();
    }

    public AssignmentDirectory(List<Assignment> assignmentList) {
        super(assignmentList);
    }
}