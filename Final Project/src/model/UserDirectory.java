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
public class UserDirectory extends Directory<User> {

    public UserDirectory() {
        super();
    }

    public UserDirectory(List<User> list){
        super(list);
    }
}
