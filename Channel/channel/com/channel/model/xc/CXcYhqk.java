package com.channel.model.xc;


import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CXcYhqkfl entity. @author MyEclipse Persistence Tools
 */

public class CXcYhqk implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer xcid;
    private Integer qk;
    private String qksm;//情况说明
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CXcYhqk() {
    }

    public CXcYhqk(Integer id, Integer xcid, Integer qk, String qksm, Integer creater, Date createtime, Date updatetime) {
        this.id = id;
        this.xcid = xcid;
        this.qk = qk;
        this.qksm = qksm;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXcYhqk(Integer xcid, Integer qk, String qksm, Integer creater, Date createtime, Date updatetime) {

        this.xcid = xcid;
        this.qk = qk;
        this.qksm = qksm;
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

    public Integer getXcid() {
        return this.xcid;
    }

    public void setXcid(Integer xcid) {
        this.xcid = xcid;
    }

    public Integer getQk() {
        return qk;
    }

    public void setQk(Integer qk) {
        this.qk = qk;
    }

    public String getQksm() {
        return qksm;
    }

    public void setQksm(String qksm) {
        this.qksm = qksm;
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