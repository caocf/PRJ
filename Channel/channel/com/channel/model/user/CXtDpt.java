package com.channel.model.user;

import java.util.Date;

import com.common.utils.tree.model.TreeNode;

/**
 * CXtDpt entity. @author MyEclipse Persistence Tools
 */

public class CXtDpt extends TreeNode implements java.io.Serializable {

    private Integer xzqh;
    private String managexzqh;//辖区
    private String defaultroles;//新增用户时的默认角色
    private String dptdesc;
    private Date createtime;
    private Date updatetime;


    private Integer subcnt;

    public String getDefaultroles() {
        return defaultroles;
    }

    public void setDefaultroles(String defaultroles) {
        this.defaultroles = defaultroles;
    }

    public Integer getSubcnt() {
        return subcnt;
    }

    public void setSubcnt(Integer subcnt) {
        this.subcnt = subcnt;
    }

    public Integer getXzqh() {
        return xzqh;
    }

    public void setXzqh(Integer xzqh) {
        this.xzqh = xzqh;
    }

    public String getDptdesc() {
        return dptdesc;
    }

    public void setDptdesc(String dptdesc) {
        this.dptdesc = dptdesc;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public CXtDpt() {
        super();
    }

    public CXtDpt(int id) {
        super();
        setId(id);
    }

    public CXtDpt(Integer xzqh, String dptdesc, Date createtime, Date updatetime) {
        super();
        this.xzqh = xzqh;
        this.dptdesc = dptdesc;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CXtDpt(int type, String name, Integer xzqh, String dptdesc,
                  Date createtime, Date updatetime) {
        super();
        super.setType(type);
        super.setName(name);
        this.xzqh = xzqh;
        this.dptdesc = dptdesc;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getManagexzqh() {
        return managexzqh;
    }

    public void setManagexzqh(String managexzqh) {
        this.managexzqh = managexzqh;
    }
}