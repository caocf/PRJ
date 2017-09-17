package com.channel.model.bb;

import java.sql.Blob;
import java.util.Date;


/**
 * CBbBb entity. @author MyEclipse Persistence Tools
 */

public class CBbJSYOriBb implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer tableindex;
    private Blob tableobj;
    private Integer creater;
    private Date creattime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CBbJSYOriBb() {
    }

    public CBbJSYOriBb(Integer tableindex, Blob tableobj) {
        this.tableindex = tableindex;
        this.tableobj = tableobj;
    }

    public CBbJSYOriBb(Integer tableindex, Blob tableobj, Integer creater, Date creattime, Date updatetime) {
        this.tableindex = tableindex;
        this.tableobj = tableobj;
        this.creater = creater;
        this.creattime = creattime;
        this.updatetime = updatetime;
    }

    public Integer getTableindex() {
        return tableindex;
    }

    public void setTableindex(Integer tableindex) {
        this.tableindex = tableindex;
    }

    public Blob getTableobj() {
        return tableobj;
    }

    public void setTableobj(Blob tableobj) {
        this.tableobj = tableobj;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}