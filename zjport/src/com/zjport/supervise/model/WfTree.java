package com.zjport.supervise.model;

public class WfTree {

    private String id;
    private String name;
    private String pid;
    private int type; //1 行政区划 2 水位点 3 流量点

    private long cnt;

    public WfTree() {
    }

    public WfTree(String id, String name, String pid, int type, long count) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.type = type;
        this.cnt = count;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }
}
