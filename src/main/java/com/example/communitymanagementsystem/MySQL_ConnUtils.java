package com.example.communitymanagementsystem;

import java.sql.*;

public class MySQL_ConnUtils {
    public boolean isConnected = false; // 数据库连接状态

    public String userRole = "未知";
    public String userName = "未知";
    public String userID = "未知"; // 当前用户ID
    Connection conn = null; // 数据库连接
    private String user;
    private String password;
    private String database_name;
    private String database_url;
    private static MySQL_ConnUtils instance; // 单例模式

    private MySQL_ConnUtils() { // 构造函数私有化
        this.user = "test";
        this.password = "_Test141760";
        this.database_url = "139.224.195.43";
        this.database_name = "community_management_system";
    }

    // 单例设计模式，获取实例
    public static MySQL_ConnUtils getInstance() { // 静态方法
        if (instance == null) { // 第一次调用getInstance方法时，创建实例
            instance = new MySQL_ConnUtils();
            instance.connectToDatabase();
        }
        return instance; // 返回实例
    }

    private void connectToDatabase() {
        System.out.println("正在尝试连接数据库...");
        try {
            System.out.println("正在加载MySQL JDBC驱动...");
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载JDBC驱动程序
            System.out.println("成功加载MySQL JDBC驱动！");
            conn = DriverManager.getConnection("jdbc:mysql://"
                    + database_url + ":3306/"
                    + database_name +
                    "?useUnicode=true&characterEncoding=UTF-8", user, password);
            System.out.println("成功连接到数据库！");
            isConnected = true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // 输出异常信息
            System.out.println("加载MySQL JDBC驱动失败！");
            System.out.println("连接数据库失败！");
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() { // 获取数据库连接
        return conn;
    }

    public void closeConnection() { // 关闭数据库连接
        System.out.println("正在关闭数据库连接...");
        try {
            if (conn != null) {
                conn.close();
                System.out.println("关闭数据库连接成功！");
            }
        } catch (SQLException e) {
            System.out.println("关闭数据库连接失败！");
            System.out.println(e.getMessage());
        }
    }

    public User getUserByUserName(String userName) {
        try {
            String sql = "SELECT * FROM usertable WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(Integer.parseInt(rs.getString("id")));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("role"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User Login(String userName, String password) {
        User user = new User();
        user = getUserByUserName(userName);
        if (user == null) {
            System.out.println("用户名不存在！");
            return null;
        }
        if (user.getPassword().equals(password)) {
            System.out.println("登录成功！");
            return user;
        } else {
            System.out.println("密码错误！");
            return null;
        }
    }
}
