package com.channel.model.hz;


import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CHzCzpc entity. @author MyEclipse Persistence Tools
 */

public class CHzCzpc implements java.io.Serializable {

    // Fields

    private Integer id;
    private String dw;
    private String name;
    private Integer hdaoid;
    private Integer xz;
    private Double pbcje;
    private String sldw;
    private String zcpgdw;
    private Date slrq;
    private Integer hduanid;
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CHzCzpc() {
    }

    /**
     * full constructor
     */
    public CHzCzpc(String dw, String name, Integer hdaoid, Integer xz,
                   Double pbcje, String sldw, String zcpgdw, Date slrq,
                   Integer hduanid, Integer creater, Date createtime,
                   Date updatetime) {
        this.dw = dw;
        this.name = name;
        this.hdaoid = hdaoid;
        this.xz = xz;
        this.pbcje = pbcje;
        this.sldw = sldw;
        this.zcpgdw = zcpgdw;
        this.slrq = slrq;
        this.hduanid = hduanid;
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

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHdaoid() {
        return this.hdaoid;
    }

    public void setHdaoid(Integer hdaoid) {
        this.hdaoid = hdaoid;
    }

    public Integer getXz() {
        return this.xz;
    }

    public void setXz(Integer xz) {
        this.xz = xz;
    }

    public Double getPbcje() {
        return this.pbcje;
    }

    public void setPbcje(Double pbcje) {
        this.pbcje = pbcje;
    }

    public String getSldw() {
        return this.sldw;
    }

    public void setSldw(String sldw) {
        this.sldw = sldw;
    }

    public String getZcpgdw() {
        return this.zcpgdw;
    }

    public void setZcpgdw(String zcpgdw) {
        this.zcpgdw = zcpgdw;
    }
    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getSlrq() {
        return this.slrq;
    }

    public void setSlrq(Date slrq) {
        this.slrq = slrq;
    }

    public Integer getHduanid() {
        return this.hduanid;
    }

    public void setHduanid(Integer hduanid) {
        this.hduanid = hduanid;
    }

    public Integer getCreater() {
        return this.creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}