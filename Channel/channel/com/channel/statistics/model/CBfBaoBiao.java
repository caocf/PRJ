package com.channel.statistics.model;

import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CBfHdaolc entity. @author MyEclipse Persistence Tools
 */

public class CBfBaoBiao implements java.io.Serializable {
    private Integer id;
    private String baobiaokey;
    private String baobiaotime;
    private String datakey;
    private String datavalue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaobiaokey() {
        return baobiaokey;
    }

    public void setBaobiaokey(String baobiaokey) {
        this.baobiaokey = baobiaokey;
    }

    public String getBaobiaotime() {
        return baobiaotime;
    }

    public void setBaobiaotime(String baobiaotime) {
        this.baobiaotime = baobiaotime;
    }

    public String getDatakey() {
        return datakey;
    }

    public void setDatakey(String datakey) {
        this.datakey = datakey;
    }

    public String getDatavalue() {
        return datavalue;
    }

    public void setDatavalue(String datavalue) {
        this.datavalue = datavalue;
    }
}