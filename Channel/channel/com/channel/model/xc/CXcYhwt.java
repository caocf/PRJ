package com.channel.model.xc;


import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CXcYhqkms entity. @author MyEclipse Persistence Tools
 */

public class CXcYhwt implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer qkid;
    private Integer wt;
    private String wtsm;
    private String clyj;
    private String cljg;
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CXcYhwt() {
    }

    public CXcYhwt(Integer id, Integer qkid, Integer wt, String wtsm, String clyj, String cljg, Integer creater, Date createtime, Date updatetime) {
        this.id = id;
        this.qkid = qkid;
        this.wt = wt;
        this.wtsm = wtsm;
        this.clyj = clyj;
        this.cljg = cljg;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXcYhwt(Integer qkid, Integer wt, String wtsm, String clyj, String cljg, Integer creater, Date createtime, Date updatetime) {

        this.qkid = qkid;
        this.wt = wt;
        this.wtsm = wtsm;
        this.clyj = clyj;
        this.cljg = cljg;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQkid() {
        return this.qkid;
    }

    public void setQkid(Integer qkid) {
        this.qkid = qkid;
    }

    public Integer getWt() {
        return wt;
    }

    public void setWt(Integer wt) {
        this.wt = wt;
    }

    public String getWtsm() {
        return wtsm;
    }

    public void setWtsm(String wtsm) {
        this.wtsm = wtsm;
    }

    public String getClyj() {
        return this.clyj;
    }

    public void setClyj(String clyj) {
        this.clyj = clyj;
    }

    public String getCljg() {
        return this.cljg;
    }

    public void setCljg(String cljg) {
        this.cljg = cljg;
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

}