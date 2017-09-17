package com.channel.model.hd;

import java.util.Date;

import com.common.utils.tree.model.TreeNode;


/**
 * CZdAppurtenance entity. @author MyEclipse Persistence Tools
 */

public class CZdAppurtenance extends TreeNode {

    // Fields

    private Integer id;
    private String name;
    private Integer type;
    private String appdesc;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CZdAppurtenance() {
    }

    /**
     * full constructor
     */
    public CZdAppurtenance(String name, Integer type, String appdesc,
                           Date createtime, Date updatetime) {
        super();
        super.setName(name);
        super.setType(type);
        ;
        this.appdesc = appdesc;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    private int fswcnt;

    public int getFswcnt() {
        return fswcnt;
    }

    public void setFswcnt(int fswcnt) {
        this.fswcnt = fswcnt;
    }

    public String getAppdesc() {
        return this.appdesc;
    }

    public void setAppdesc(String appdesc) {
        this.appdesc = appdesc;
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