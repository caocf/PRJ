package com.channel.model.hz;


import java.util.Date;

/**
 * CHzCzpclx entity. @author MyEclipse Persistence Tools
 */

public class CHzCzpclx implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer pid;
    private Integer dfl;
    private Integer xfl;
    private Integer sx;
    private Double sl;
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CHzCzpclx() {
    }

    /**
     * minimal constructor
     */
    public CHzCzpclx(Integer pid) {
        this.pid = pid;
    }

    public CHzCzpclx(Integer pid, Integer dfl, Integer xfl, Integer sx, Double sl, Integer creater, Date createtime, Date updatetime) {
        this.pid = pid;
        this.dfl = dfl;
        this.xfl = xfl;
        this.sx = sx;
        this.sl = sl;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHzCzpclx(Integer id, Integer pid, Integer dfl, Integer xfl, Integer sx, Double sl, Integer creater, Date createtime, Date updatetime) {

        this.id = id;
        this.pid = pid;
        this.dfl = dfl;
        this.xfl = xfl;
        this.sx = sx;
        this.sl = sl;
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

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public Integer getDfl() {
        return dfl;
    }

    public void setDfl(Integer dfl) {
        this.dfl = dfl;
    }

    public Integer getXfl() {
        return xfl;
    }

    public void setXfl(Integer xfl) {
        this.xfl = xfl;
    }

    public Integer getSx() {
        return sx;
    }

    public void setSx(Integer sx) {
        this.sx = sx;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
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