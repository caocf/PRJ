package com.channel.model.ll;


import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CLlHdllgc entity. @author MyEclipse Persistence Tools
 */

public class CLlHdllgc implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer gczid;
    private Integer type;
    private Date starttime;
    private Date endtime;
    private Integer upcbnum;
    private Integer uptlnum;
    private Integer upjdbnum;
    private Integer upfjdbnum;
    private Integer upqtcnum;
    private Integer downcbnum;
    private Integer downtlnum;
    private Integer downjdbnum;
    private Integer downfjdbnum;
    private Integer downqtcnum;
    private Double upcbton;
    private Double uptlton;
    private Double upjdbton;
    private Double upfjdbton;
    private Double upqtcton;
    private Double downcbton;
    private Double downtlton;
    private Double downjdbton;
    private Double downfjdbton;
    private Double downqtcton;
    private Double upgoodston;
    private Double upmtton;
    private Double upkjclton;
    private Double upqtton;
    private Double downgoodston;
    private Double downmtton;
    private Double downkjclton;
    private Double downqtton;
    private Integer timeflag;
    private Integer creater;
    private Date creattime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CLlHdllgc() {
    }

    /**
     * minimal constructor
     */
    public CLlHdllgc(Integer id, Integer gczid, Integer type,
                     Date starttime, Date endtime, Integer creater,
                     Date creattime, Date updatetime) {
        this.id = id;
        this.gczid = gczid;
        this.type = type;
        this.starttime = starttime;
        this.endtime = endtime;
        this.creater = creater;
        this.creattime = creattime;
        this.updatetime = updatetime;
    }

    /**
     * full constructor
     */
    public CLlHdllgc(Integer id, Integer gczid, Integer type,
                     Date starttime, Date endtime, Integer upcbnum,
                     Integer uptlnum, Integer upjdbnum, Integer upfjdbnum,
                     Integer upqtcnum, Integer downcbnum, Integer downtlnum,
                     Integer downjdbnum, Integer downfjdbnum, Integer downqtcnum,
                     Double upcbton, Double uptlton, Double upjdbton, Double upfjdbton,
                     Double upqtcton, Double downcbton, Double downtlton,
                     Double downjdbton, Double downfjdbton, Double downqtcton,
                     Double upgoodston, Double upmtton, Double upkjclton,
                     Double upqtton, Double downgoodston, Double downmtton,
                     Double downkjclton, Double downqtton, Integer creater,
                     Date creattime, Date updatetime) {
        this.id = id;
        this.gczid = gczid;
        this.type = type;
        this.starttime = starttime;
        this.endtime = endtime;
        this.upcbnum = upcbnum;
        this.uptlnum = uptlnum;
        this.upjdbnum = upjdbnum;
        this.upfjdbnum = upfjdbnum;
        this.upqtcnum = upqtcnum;
        this.downcbnum = downcbnum;
        this.downtlnum = downtlnum;
        this.downjdbnum = downjdbnum;
        this.downfjdbnum = downfjdbnum;
        this.downqtcnum = downqtcnum;
        this.upcbton = upcbton;
        this.uptlton = uptlton;
        this.upjdbton = upjdbton;
        this.upfjdbton = upfjdbton;
        this.upqtcton = upqtcton;
        this.downcbton = downcbton;
        this.downtlton = downtlton;
        this.downjdbton = downjdbton;
        this.downfjdbton = downfjdbton;
        this.downqtcton = downqtcton;
        this.upgoodston = upgoodston;
        this.upmtton = upmtton;
        this.upkjclton = upkjclton;
        this.upqtton = upqtton;
        this.downgoodston = downgoodston;
        this.downmtton = downmtton;
        this.downkjclton = downkjclton;
        this.downqtton = downqtton;
        this.creater = creater;
        this.creattime = creattime;
        this.updatetime = updatetime;
    }

    public CLlHdllgc(Integer id, Integer gczid, Integer type, Date starttime, Date endtime, Integer upcbnum, Integer uptlnum, Integer upjdbnum, Integer upfjdbnum, Integer upqtcnum, Integer downcbnum, Integer downtlnum, Integer downjdbnum, Integer downfjdbnum, Integer downqtcnum, Double upcbton, Double uptlton, Double upjdbton, Double upfjdbton, Double upqtcton, Double downcbton, Double downtlton, Double downjdbton, Double downfjdbton, Double downqtcton, Double upgoodston, Double upmtton, Double upkjclton, Double upqtton, Double downgoodston, Double downmtton, Double downkjclton, Double downqtton, Integer timeflag, Integer creater, Date creattime, Date updatetime) {
        this.id = id;
        this.gczid = gczid;
        this.type = type;
        this.starttime = starttime;
        this.endtime = endtime;
        this.upcbnum = upcbnum;
        this.uptlnum = uptlnum;
        this.upjdbnum = upjdbnum;
        this.upfjdbnum = upfjdbnum;
        this.upqtcnum = upqtcnum;
        this.downcbnum = downcbnum;
        this.downtlnum = downtlnum;
        this.downjdbnum = downjdbnum;
        this.downfjdbnum = downfjdbnum;
        this.downqtcnum = downqtcnum;
        this.upcbton = upcbton;
        this.uptlton = uptlton;
        this.upjdbton = upjdbton;
        this.upfjdbton = upfjdbton;
        this.upqtcton = upqtcton;
        this.downcbton = downcbton;
        this.downtlton = downtlton;
        this.downjdbton = downjdbton;
        this.downfjdbton = downfjdbton;
        this.downqtcton = downqtcton;
        this.upgoodston = upgoodston;
        this.upmtton = upmtton;
        this.upkjclton = upkjclton;
        this.upqtton = upqtton;
        this.downgoodston = downgoodston;
        this.downmtton = downmtton;
        this.downkjclton = downkjclton;
        this.downqtton = downqtton;
        this.timeflag = timeflag;
        this.creater = creater;
        this.creattime = creattime;
        this.updatetime = updatetime;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGczid() {
        return this.gczid;
    }

    public void setGczid(Integer gczid) {
        this.gczid = gczid;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getStarttime() {
        return this.starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getEndtime() {
        return this.endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getUpcbnum() {
        return this.upcbnum;
    }

    public void setUpcbnum(Integer upcbnum) {
        this.upcbnum = upcbnum;
    }

    public Integer getUptlnum() {
        return this.uptlnum;
    }

    public void setUptlnum(Integer uptlnum) {
        this.uptlnum = uptlnum;
    }

    public Integer getUpjdbnum() {
        return this.upjdbnum;
    }

    public void setUpjdbnum(Integer upjdbnum) {
        this.upjdbnum = upjdbnum;
    }

    public Integer getUpfjdbnum() {
        return this.upfjdbnum;
    }

    public void setUpfjdbnum(Integer upfjdbnum) {
        this.upfjdbnum = upfjdbnum;
    }

    public Integer getUpqtcnum() {
        return this.upqtcnum;
    }

    public void setUpqtcnum(Integer upqtcnum) {
        this.upqtcnum = upqtcnum;
    }

    public Integer getDowncbnum() {
        return this.downcbnum;
    }

    public void setDowncbnum(Integer downcbnum) {
        this.downcbnum = downcbnum;
    }

    public Integer getDowntlnum() {
        return this.downtlnum;
    }

    public void setDowntlnum(Integer downtlnum) {
        this.downtlnum = downtlnum;
    }

    public Integer getDownjdbnum() {
        return this.downjdbnum;
    }

    public void setDownjdbnum(Integer downjdbnum) {
        this.downjdbnum = downjdbnum;
    }

    public Integer getDownfjdbnum() {
        return this.downfjdbnum;
    }

    public void setDownfjdbnum(Integer downfjdbnum) {
        this.downfjdbnum = downfjdbnum;
    }

    public Integer getDownqtcnum() {
        return this.downqtcnum;
    }

    public void setDownqtcnum(Integer downqtcnum) {
        this.downqtcnum = downqtcnum;
    }

    public Double getUpcbton() {
        return this.upcbton;
    }

    public void setUpcbton(Double upcbton) {
        this.upcbton = upcbton;
    }

    public Double getUptlton() {
        return this.uptlton;
    }

    public void setUptlton(Double uptlton) {
        this.uptlton = uptlton;
    }

    public Double getUpjdbton() {
        return this.upjdbton;
    }

    public void setUpjdbton(Double upjdbton) {
        this.upjdbton = upjdbton;
    }

    public Double getUpfjdbton() {
        return this.upfjdbton;
    }

    public void setUpfjdbton(Double upfjdbton) {
        this.upfjdbton = upfjdbton;
    }

    public Double getUpqtcton() {
        return this.upqtcton;
    }

    public void setUpqtcton(Double upqtcton) {
        this.upqtcton = upqtcton;
    }

    public Double getDowncbton() {
        return this.downcbton;
    }

    public void setDowncbton(Double downcbton) {
        this.downcbton = downcbton;
    }

    public Double getDowntlton() {
        return this.downtlton;
    }

    public void setDowntlton(Double downtlton) {
        this.downtlton = downtlton;
    }

    public Double getDownjdbton() {
        return this.downjdbton;
    }

    public void setDownjdbton(Double downjdbton) {
        this.downjdbton = downjdbton;
    }

    public Double getDownfjdbton() {
        return this.downfjdbton;
    }

    public void setDownfjdbton(Double downfjdbton) {
        this.downfjdbton = downfjdbton;
    }

    public Double getDownqtcton() {
        return this.downqtcton;
    }

    public void setDownqtcton(Double downqtcton) {
        this.downqtcton = downqtcton;
    }

    public Double getUpgoodston() {
        return this.upgoodston;
    }

    public void setUpgoodston(Double upgoodston) {
        this.upgoodston = upgoodston;
    }

    public Double getUpmtton() {
        return this.upmtton;
    }

    public void setUpmtton(Double upmtton) {
        this.upmtton = upmtton;
    }

    public Double getUpkjclton() {
        return this.upkjclton;
    }

    public void setUpkjclton(Double upkjclton) {
        this.upkjclton = upkjclton;
    }

    public Double getUpqtton() {
        return this.upqtton;
    }

    public void setUpqtton(Double upqtton) {
        this.upqtton = upqtton;
    }

    public Double getDowngoodston() {
        return this.downgoodston;
    }

    public void setDowngoodston(Double downgoodston) {
        this.downgoodston = downgoodston;
    }

    public Double getDownmtton() {
        return this.downmtton;
    }

    public void setDownmtton(Double downmtton) {
        this.downmtton = downmtton;
    }

    public Double getDownkjclton() {
        return this.downkjclton;
    }

    public void setDownkjclton(Double downkjclton) {
        this.downkjclton = downkjclton;
    }

    public Double getDownqtton() {
        return this.downqtton;
    }

    public void setDownqtton(Double downqtton) {
        this.downqtton = downqtton;
    }

    public Integer getCreater() {
        return this.creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreattime() {
        return this.creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getTimeflag() {
        return timeflag;
    }

    public void setTimeflag(Integer timeflag) {
        this.timeflag = timeflag;
    }
}