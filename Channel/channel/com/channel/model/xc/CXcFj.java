package com.channel.model.xc;

import java.util.Date;


public class CXcFj implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer xcid;
    private Integer ftype;
    private String fname;
    private String fsize;
    private String fmd5;
    private String fpath;
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors


    public CXcFj(Integer id, Integer xcid, Integer ftype, String fname, String fsize, String fmd5, String fpath, Integer creater, Date createtime, Date updatetime) {
        this.id = id;
        this.xcid = xcid;
        this.ftype = ftype;
        this.fname = fname;
        this.fsize = fsize;
        this.fmd5 = fmd5;
        this.fpath = fpath;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXcFj(Integer xcid, Integer ftype, String fname, String fsize, String fmd5, String fpath, Integer creater, Date createtime, Date updatetime) {

        this.xcid = xcid;
        this.ftype = ftype;
        this.fname = fname;
        this.fsize = fsize;
        this.fmd5 = fmd5;
        this.fpath = fpath;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXcFj() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getXcid() {
        return xcid;
    }

    public void setXcid(Integer xcid) {
        this.xcid = xcid;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public String getFmd5() {
        return fmd5;
    }

    public void setFmd5(String fmd5) {
        this.fmd5 = fmd5;
    }

    public String getFpath() {
        return fpath;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}