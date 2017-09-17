package com.channel.bean;

import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/4.
 */
public class HdaoBean {
    private int xzqh;//
    private String name;//名称
    private int ggnum;
    private double gglc;
    private int zxnum;
    private double zxlc;
    private int totalnum;
    private double totallc;

    public int getXzqh() {
        return xzqh;
    }

    public void setXzqh(int xzqh) {
        this.xzqh = xzqh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGgnum() {
        return ggnum;
    }

    public void setGgnum(int ggnum) {
        this.ggnum = ggnum;
    }

    public double getGglc() {
        return gglc;
    }

    public void setGglc(double gglc) {
        this.gglc = gglc;
    }

    public int getZxnum() {
        return zxnum;
    }

    public void setZxnum(int zxnum) {
        this.zxnum = zxnum;
    }

    public double getZxlc() {
        return zxlc;
    }

    public void setZxlc(double zxlc) {
        this.zxlc = zxlc;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public double getTotallc() {
        return totallc;
    }

    public void setTotallc(double totallc) {
        this.totallc = totallc;
    }

    public HdaoBean() {
    }

    public HdaoBean(int xzqh, String name, int ggnum, double gglc, int zxnum, double zxlc, int totalnum, double totallc) {
        this.xzqh = xzqh;
        this.name = name;
        this.ggnum = ggnum;
        this.gglc = gglc;
        this.zxnum = zxnum;
        this.zxlc = zxlc;
        this.totalnum = totalnum;
        this.totallc = totallc;
    }
}
