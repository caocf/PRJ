package com.zjport.log.model;

import java.sql.Timestamp;


public class Log
{
    private int id;// 自增ID
    private int optUser;//操作人ID
    private String logContent;//日志内容
    private Timestamp logTime;//日志时间

    public Log(){

    }

    public Log(int optUser, String logContent, Timestamp logTime){
        this.optUser = optUser;
        this.logContent = logContent;
        this.logTime = logTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOptUser() {
        return optUser;
    }

    public void setOptUser(int optUser) {
        this.optUser = optUser;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

}
