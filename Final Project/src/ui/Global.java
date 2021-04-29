package ui;

import model.User;

import javax.swing.*;

/**
 * a global object
 *
 * @author Joseph Yuanhao Li
 * @date 4/28/21 21:26
 */
public class Global {
    private static Global instance = null;

    public synchronized static Global getInstance(){
        if (instance == null){
            instance = new Global();
        }

        return instance;
    }

    private User user; // current login user

    private JFrame frame;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }


    public void login(User user){
        this.user = user;
    }

    public void logout(){
        this.user = null;
        Router.getInstance(null).back(-1);
    }
}
