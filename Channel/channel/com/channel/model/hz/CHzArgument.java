package com.channel.model.hz;


import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CHzArgument entity. @author MyEclipse Persistence Tools
 */

public class CHzArgument implements java.io.Serializable {

    // Fields

    private Integer id;
    private String name;
    private Date atime;
    private String pname;
    private String title;
    private Integer hdao;
    private Integer creater;
    private Date createtime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CHzArgument() {
    }

    /**
     * minimal constructor
     */
    public CHzArgument(String name, Date atime, String title) {
        this.name = name;
        this.atime = atime;
        this.title = title;
    }

    /**
     * full constructor
     */
    public CHzArgument(String name, Date atime, String pname,
                       String title, Integer creater, Date createtime,
                       Date updatetime) {
        this.name = name;
        this.atime = atime;
        this.pname = pname;
        this.title = title;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHzArgument(Integer id, String name, Date atime, String pname, String title, Integer hdao, Integer creater, Date createtime, Date updatetime) {
        this.id = id;
        this.name = name;
        this.atime = atime;
        this.pname = pname;
        this.title = title;
        this.hdao = hdao;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }
// Property accessors

    public Integer getHdao() {
        return hdao;
    }

    public void setHdao(Integer hdao) {
        this.hdao = hdao;
    }

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

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getAtime() {
        return this.atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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