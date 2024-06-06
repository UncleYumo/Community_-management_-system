package com.example.communitymanagementsystem;

import java.sql.Date;

public class Property_Request {
    private int requestID;
    private String username;
    private String description;
    private String phone;
    private Date startTime;
    private Date endTime;
    private String status;

    public Property_Request() {
    }

    public Property_Request(int requestID, String username, String description, String phone, Date startTime, Date endTime, String status) {
        this.requestID = requestID;
        this.username = username;
        this.description = description;
        this.phone = phone;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
