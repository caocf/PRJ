package com.zjport.log.model;

public class LogModel
{
    private int id;// 自增ID
    private String name;//操作人ID
    private String content;//日志内容
    private String jlsj;//日志时间

    public LogModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJlsj() {
        return jlsj;
    }

    public void setJlsj(String jlsj) {
        this.jlsj = jlsj;
    }
}
