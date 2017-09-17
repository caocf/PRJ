package com.channel.bean;

/**
 * Created by Administrator on 2015/12/24.
 */
public class ThlzFj {
    private Integer fid;
    private String fname;
    private Integer ftype;
    private String fsize;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public ThlzFj() {
    }

    public ThlzFj(Integer fid, String fname, Integer ftype, String fsize) {
        this.fid = fid;
        this.fname = fname;
        this.ftype = ftype;
        this.fsize = fsize;
    }
}
