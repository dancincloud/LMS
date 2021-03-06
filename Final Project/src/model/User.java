package model;

import DBUtil.InstructorDBUtil;
import DBUtil.StudentDBUtil;

/**
 * @author Joseph Yuanhao Li
 * @date 4/27/21 21:56
 */
public class User {

    static public User authenticateUser(String username, String password, int type) {
        if (type == 1) {
            // search in db
            Instructor instructor = InstructorDBUtil.selectByUsername(username);

            return instructor;
        } else {
            // search in db
            Student student = StudentDBUtil.selectByUsername(username);
            return student;
        }
    }

    private String id;
    private String name;
    private String email;
    private String username;
    private String password;
    private long createTime;
    private long updateTime;

    private int type = 0; // 0 - Student 1 - Instructor

    public User(){}

    public User(String id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return String.format("id = %10s, name = %10s, email = %20s, username = %10s, password = %20s", id, name, email, username, password);
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
