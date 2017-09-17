package com.channel.bean;

import com.channel.model.hd.CHdHduanjcxx;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class TrafficDT implements Serializable ,Comparable<TrafficDT>{
    private Integer id;
    private Integer gczid;
    private Integer type;
    private String  mc;
    private Date starttime;
    private Date endtime;
    private Integer upcbnum;
    private Double upcbton;
    private Double upgoodston;
    private Integer downcbnum;
    private Double downcbton;
    private Double downgoodston;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGczid() {
        return gczid;
    }

    public void setGczid(Integer gczid) {
        this.gczid = gczid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @JSON(format = "yyyy-MM-dd")
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }
    @JSON(format = "yyyy-MM-dd")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getUpcbnum() {
        return upcbnum;
    }

    public void setUpcbnum(Integer upcbnum) {
        this.upcbnum = upcbnum;
    }

    public Double getUpcbton() {
        return upcbton;
    }

    public void setUpcbton(Double upcbton) {
        this.upcbton = upcbton;
    }

    public Double getUpgoodston() {
        return upgoodston;
    }

    public void setUpgoodston(Double upgoodston) {
        this.upgoodston = upgoodston;
    }

    public Integer getDowncbnum() {
        return downcbnum;
    }

    public void setDowncbnum(Integer downcbnum) {
        this.downcbnum = downcbnum;
    }

    public Double getDowncbton() {
        return downcbton;
    }

    public void setDowncbton(Double downcbton) {
        this.downcbton = downcbton;
    }

    public Double getDowngoodston() {
        return downgoodston;
    }

    public void setDowngoodston(Double downgoodston) {
        this.downgoodston = downgoodston;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public TrafficDT() {
    }

    public TrafficDT(Integer gczid, Integer type, String mc, Date starttime, Date endtime, Integer upcbnum, Double upcbton, Double upgoodston, Integer downcbnum, Double downcbton, Double downgoodston, Integer id) {
        this.gczid = gczid;
        this.type = type;
        this.mc = mc;
        this.starttime = starttime;
        this.endtime = endtime;
        this.upcbnum = upcbnum;
        this.upcbton = upcbton;
        this.upgoodston = upgoodston;
        this.downcbnum = downcbnum;
        this.downcbton = downcbton;
        this.downgoodston = downgoodston;
        this.id = id;
    }

    @Override
    public int compareTo(TrafficDT o) {
        if (this.getStarttime().before(o.getStarttime())) {
            return 1;
        }
        if (this.getStarttime().after(o.getStarttime())) {
            return -1;
        }
        return 0;
    }
}
