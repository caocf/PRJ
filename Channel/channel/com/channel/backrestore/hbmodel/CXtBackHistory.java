package com.channel.backrestore.hbmodel;

import java.util.Date;

/**
 * Created by 25019 on 2015/10/23.
 */
public class CXtBackHistory {
    // 自增ID
    private int id;

    // 备份计划ID
    private int backjobid;

    //备份时间
    private Date backtime;

    // 备份文件路径
    private String backfilename;

    // 备份文件MD5
    private String backfilemd5;


    //非hibernate属性
    private long backfilesize; //备份文件大小
    private String backfilestatus; //备份文件状态，有效或已损坏

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBackjobid() {
        return backjobid;
    }

    public void setBackjobid(int backjobid) {
        this.backjobid = backjobid;
    }

    public Date getBacktime() {
        return backtime;
    }

    public void setBacktime(Date backtime) {
        this.backtime = backtime;
    }

    public String getBackfilemd5() {
        return backfilemd5;
    }

    public void setBackfilemd5(String backfilemd5) {
        this.backfilemd5 = backfilemd5;
    }

    public long getBackfilesize() {
        return backfilesize;
    }

    public void setBackfilesize(long backfilesize) {
        this.backfilesize = backfilesize;
    }

    public String getBackfilename() {
        return backfilename;
    }

    public void setBackfilename(String backfilename) {
        this.backfilename = backfilename;
    }

    public String getBackfilestatus() {
        return backfilestatus;
    }

    public void setBackfilestatus(String backfilestatus) {
        this.backfilestatus = backfilestatus;
    }
}
