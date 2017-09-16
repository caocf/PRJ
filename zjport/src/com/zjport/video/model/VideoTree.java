package com.zjport.video.model;

public class VideoTree {

    private String id;
    private String name;
    private String pid;
    private int type; //1 根目录 2 航道 3 航段 4 航段视频 5 码头视频 6 观测点视频 7视频

    public VideoTree() {
    }

    public VideoTree(String id, String name, String pid, int type) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.type = type;
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
}
