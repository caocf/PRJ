package com.channel.model.xc;


import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CXcQkwt entity. @author MyEclipse Persistence Tools
 */

public class CXcSj implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer wtid;
    private String jtwz;
    private Integer ab;
    private String ms;
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CXcSj() {
    }

    /**
     * minimal constructor
     */
    public CXcSj(Integer wtid, String jtwz, Integer ab) {
        this.wtid = wtid;
        this.jtwz = jtwz;
        this.ab = ab;
    }

    /**
     * full constructor
     */
    public CXcSj(Integer wtid, String jtwz, Integer ab, String ms,
                 Integer creater, Date createtime, Date updatetime) {
        this.wtid = wtid;
        this.jtwz = jtwz;
        this.ab = ab;
        this.ms = ms;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWtid() {
        return this.wtid;
    }

    public void setWtid(Integer wtid) {
        this.wtid = wtid;
    }

    public String getJtwz() {
        return this.jtwz;
    }

    public void setJtwz(String jtwz) {
        this.jtwz = jtwz;
    }

    public Integer getAb() {
        return this.ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
    }

    public String getMs() {
        return this.ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
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