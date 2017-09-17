package com.channel.model.yh;

import org.apache.struts2.json.annotations.JSON;

import java.util.Date;


/**
 * CYhYjqtgc entity. @author MyEclipse Persistence Tools
 */

public class CYhYjqtgc implements java.io.Serializable {

    // Fields

    private Integer id;
    private String name;
    private String eventdesc;
    private Date starttime;
    private Integer mandept;
    private Integer hdaoid;
    private Integer hduanid;
    private String address;
    private String losecase;
    private String mainreason;
    private String realcase;
    private String participants;
    private Double cost;
    private Date restoretime;
    private String remark;
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CYhYjqtgc() {
    }

    /**
     * minimal constructor
     */
    public CYhYjqtgc(String name, Integer creater, Date createtime,
                     Date updatetime) {
        this.name = name;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    /**
     * full constructor
     */
    public CYhYjqtgc(String name, String eventdesc, Date starttime,
                     Integer mandept, Integer hdaoid, Integer hduanid, String address,
                     String losecase, String mainreason, String realcase,
                     String participants, Double cost, Date restoretime,
                     String remark, Integer creater, Date createtime,
                     Date updatetime) {
        this.name = name;
        this.eventdesc = eventdesc;
        this.starttime = starttime;
        this.mandept = mandept;
        this.hdaoid = hdaoid;
        this.hduanid = hduanid;
        this.address = address;
        this.losecase = losecase;
        this.mainreason = mainreason;
        this.realcase = realcase;
        this.participants = participants;
        this.cost = cost;
        this.restoretime = restoretime;
        this.remark = remark;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventdesc() {
        return this.eventdesc;
    }

    public void setEventdesc(String eventdesc) {
        this.eventdesc = eventdesc;
    }
    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getStarttime() {
        return this.starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getMandept() {
        return this.mandept;
    }

    public void setMandept(Integer mandept) {
        this.mandept = mandept;
    }

    public Integer getHdaoid() {
        return this.hdaoid;
    }

    public void setHdaoid(Integer hdaoid) {
        this.hdaoid = hdaoid;
    }

    public Integer getHduanid() {
        return this.hduanid;
    }

    public void setHduanid(Integer hduanid) {
        this.hduanid = hduanid;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLosecase() {
        return this.losecase;
    }

    public void setLosecase(String losecase) {
        this.losecase = losecase;
    }

    public String getMainreason() {
        return this.mainreason;
    }

    public void setMainreason(String mainreason) {
        this.mainreason = mainreason;
    }

    public String getRealcase() {
        return this.realcase;
    }

    public void setRealcase(String realcase) {
        this.realcase = realcase;
    }

    public String getParticipants() {
        return this.participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getRestoretime() {
        return this.restoretime;
    }

    public void setRestoretime(Date restoretime) {
        this.restoretime = restoretime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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