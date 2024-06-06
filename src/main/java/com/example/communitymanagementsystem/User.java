package com.example.communitymanagementsystem;

/**
 * @author uncle_yumo
 * @CreateDate 2024/6/6
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private String userRole;

    public User(int userId, String userName, String password, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    public User(String userName, String password, String userRole) {
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public String userRoleToString() {
        if (userRole.equals("admin")) {
            return "管理员";
        } else if (userRole.equals("resident")){
            return "业主";
        }
        return "权限未知";
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
