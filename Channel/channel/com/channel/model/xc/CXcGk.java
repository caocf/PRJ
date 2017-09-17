package com.channel.model.xc;


import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CXcGk entity. @author MyEclipse Persistence Tools
 */

public class CXcGk implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer hdid;
    private Integer ztqk;
    private Date starttime;
    private Date endtime;
    private String qd;
    private String zd;
    private String qdzh;
    private String zdzh;
    private String xhth;
    private String cyr;
    private Integer dept;
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CXcGk() {
    }

    public CXcGk(Integer hdid, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String cyr, Integer creater, Date createtime, Date updatetime) {
        this.hdid = hdid;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.cyr = cyr;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXcGk(Integer id, Integer hdid, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String cyr, Integer creater, Date createtime, Date updatetime) {

        this.id = id;
        this.hdid = hdid;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.cyr = cyr;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXcGk(Integer hdid, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String cyr, Integer dept, Integer creater, Date createtime, Date updatetime) {
        this.hdid = hdid;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.cyr = cyr;
        this.dept = dept;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXcGk(Integer id, Integer hdid, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String cyr, Integer dept, Integer creater, Date createtime, Date updatetime) {

        this.id = id;
        this.hdid = hdid;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.cyr = cyr;
        this.dept = dept;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXcGk(Integer id, Integer hdid, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String xhth, String cyr, Integer dept, Integer creater, Date createtime, Date updatetime) {
        this.id = id;
        this.hdid = hdid;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.xhth = xhth;
        this.cyr = cyr;
        this.dept = dept;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHdid() {
        return this.hdid;
    }

    public void setHdid(Integer hdid) {
        this.hdid = hdid;
    }

    public Integer getZtqk() {
        return this.ztqk;
    }

    public void setZtqk(Integer ztqk) {
        this.ztqk = ztqk;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getStarttime() {
        return this.starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getEndtime() {
        return this.endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getQd() {
        return this.qd;
    }

    public void setQd(String qd) {
        this.qd = qd;
    }

    public String getZd() {
        return this.zd;
    }

    public void setZd(String zd) {
        this.zd = zd;
    }

    public String getQdzh() {
        return this.qdzh;
    }

    public void setQdzh(String qdzh) {
        this.qdzh = qdzh;
    }

    public String getZdzh() {
        return this.zdzh;
    }

    public void setZdzh(String zdzh) {
        this.zdzh = zdzh;
    }

    public String getCyr() {
        return this.cyr;
    }

    public void setCyr(String cyr) {
        this.cyr = cyr;
    }

    public Integer getCreater() {
        return this.creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getXhth() {
        return xhth;
    }

    public void setXhth(String xhth) {
        this.xhth = xhth;
    }
}