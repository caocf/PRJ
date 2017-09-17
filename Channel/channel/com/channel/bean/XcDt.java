package com.channel.bean;

import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/9.
 */
public class XcDt {
    private int id;
    private String hdmc;
    private String qd;
    private String zd;
    private String qdzh;
    private String zdzh;
    private String ztqk;
    private String deptname;
    private Date starttime;
    private Date endtime;
    private int yhgkid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHdmc() {
        return hdmc;
    }

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }

    public String getQd() {
        return qd;
    }

    public void setQd(String qd) {
        this.qd = qd;
    }

    public String getZd() {
        return zd;
    }

    public void setZd(String zd) {
        this.zd = zd;
    }

    public String getQdzh() {
        return qdzh;
    }

    public void setQdzh(String qdzh) {
        this.qdzh = qdzh;
    }

    public String getZdzh() {
        return zdzh;
    }

    public void setZdzh(String zdzh) {
        this.zdzh = zdzh;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getZtqk() {
        return ztqk;
    }

    public void setZtqk(String ztqk) {
        this.ztqk = ztqk;
    }
    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }
    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public int getYhgkid() {
        return yhgkid;
    }

    public void setYhgkid(int yhgkid) {
        this.yhgkid = yhgkid;
    }
}
