/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ke
 */
public class StudentDirectory extends Directory<Student> {

    public StudentDirectory() {
        super();
    }

    public StudentDirectory(List<Student> list){
        super(list);
    }
}
