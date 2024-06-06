package com.example.communitymanagementsystem;

import java.sql.*;
import java.util.ArrayList;

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

    public User currentUser;

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
            currentUser = user;
            return user;
        } else {
            System.out.println("密码错误！");
            return null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getAllUser() {
        try {
            String sql = "SELECT * FROM usertable";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<User> userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUserId(Integer.parseInt(rs.getString("id")));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("role"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addUser(User newUser) {
        // 输入username、password、role，插入数据库
        try {
            String sql = "INSERT INTO usertable(username,password,role) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newUser.getUserName());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getUserRole());

            pstmt.executeUpdate();
            System.out.println("添加用户成功！");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delUserByUserName(String userName) {
        try {
            String sql = "DELETE FROM usertable WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.executeUpdate();
            System.out.println("删除用户成功！");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modifyUser(User newUser) {
        try {
            String sql = "UPDATE usertable SET username = ?, password = ?, role = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newUser.getUserName());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getUserRole());
            pstmt.setInt(4, newUser.getUserId());
            pstmt.executeUpdate();
            System.out.println("修改用户成功！");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> queryUserByUserName(String username) {
        // 模糊查询“username”

        try {
            String sql = "SELECT * FROM usertable WHERE username LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + username + "%");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<User> userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUserId(Integer.parseInt(rs.getString("id")));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("role"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Property_Request> getPropertyRequestByCurrentUser() {
        try {
            String sql = "SELECT * FROM property_requests WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, currentUser.getUserName());
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Property_Request> propertyRequestList = new ArrayList<>();
            while (rs.next()) {
                Property_Request propertyRequest = new Property_Request();
                propertyRequest.setRequestID(Integer.parseInt(rs.getString("requestID")));
                propertyRequest.setUsername(rs.getString("username"));
                propertyRequest.setPhone(rs.getString("phone"));
                propertyRequest.setDescription(rs.getString("description"));
                propertyRequest.setStartTime(rs.getDate("startTime"));
                propertyRequest.setEndTime(rs.getDate("endTime"));
                propertyRequest.setStatus(rs.getString("status"));
                propertyRequestList.add(propertyRequest);
            }
            return propertyRequestList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addPropertyRequest(String description, String phone) {
        String username = currentUser.getUserName();
        Date startTime = new Date(System.currentTimeMillis());
        // 插入新数据，包含：username、phone、description、startTime
        try {
            String sql = "INSERT INTO property_requests(username,phone,description,startTime) VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, phone);
            pstmt.setString(3, description);
            pstmt.setDate(4, startTime);
            pstmt.executeUpdate();
            System.out.println("添加请求成功！");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Property_Request> getAllPropertyRequest() {
        // 获取所有的请求记录
        try {
            String sql = "SELECT * FROM property_requests";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Property_Request> propertyRequestList = new ArrayList<>();
            while (rs.next()) {
                Property_Request propertyRequest = new Property_Request();
                propertyRequest.setRequestID(Integer.parseInt(rs.getString("requestID")));
                propertyRequest.setUsername(rs.getString("username"));
                propertyRequest.setPhone(rs.getString("phone"));
                propertyRequest.setDescription(rs.getString("description"));
                propertyRequest.setStartTime(rs.getDate("startTime"));
                propertyRequest.setEndTime(rs.getDate("endTime"));
                propertyRequest.setStatus(rs.getString("status"));
                propertyRequestList.add(propertyRequest);
            }
            return propertyRequestList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void confirmEventByRequestID(int requestID) {
        // 通过requestID确认事件,补充对应的信息：解决时间（endTime）、状态（“已处理”）
        try {
            String sql = "UPDATE property_requests SET endTime = ?, status = ? WHERE requestID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            Date endTime = new Date(System.currentTimeMillis());
            pstmt.setDate(1, endTime);
            pstmt.setString(2, "已处理");
            pstmt.setInt(3, requestID);
            pstmt.executeUpdate();
            System.out.println("确认事件成功！");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
