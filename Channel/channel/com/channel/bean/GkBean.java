package com.channel.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
public class GkBean {
    private Integer hdid;//航道id
    private Integer ztqk;//总体情况
    private Date starttime;//开始时间
    private Date endtime;//结束时间
    private String qd;//起点
    private String zd;//终点
    private String qdzh;//起点桩号
    private String zdzh;//终点桩号
    private String cyr;//参与人
    private List<QkBean> qks;//情况

    public Integer getHdid() {
        return hdid;
    }

    public void setHdid(Integer hdid) {
        this.hdid = hdid;
    }

    public Integer getZtqk() {
        return ztqk;
    }

    public void setZtqk(Integer ztqk) {
        this.ztqk = ztqk;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getQd() {
        return qd;
    }

    public void setQd(String qd) {
        this.qd = qd;
    }

    public String getZd() {
        return zd;
    }

    public void setZd(String zd) {
        this.zd = zd;
    }

    public String getQdzh() {
        return qdzh;
    }

    public void setQdzh(String qdzh) {
        this.qdzh = qdzh;
    }

    public String getZdzh() {
        return zdzh;
    }

    public void setZdzh(String zdzh) {
        this.zdzh = zdzh;
    }

    public String getCyr() {
        return cyr;
    }

    public void setCyr(String cyr) {
        this.cyr = cyr;
    }

    public List<QkBean> getQks() {
        return qks;
    }

    public void setQks(List<QkBean> qks) {
        this.qks = qks;
    }
}
