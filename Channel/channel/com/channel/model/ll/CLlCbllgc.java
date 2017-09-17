package com.channel.model.ll;

import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CLlCbllgc entity. @author MyEclipse Persistence Tools
 */

public class CLlCbllgc implements java.io.Serializable {

    // Fields

    private Integer id;
    private Integer gcdid;
    private Integer type;
    private Date starttime;
    private Date endtime;
    private String shipname;
    private Integer shipdire;
    private Integer shiptype;
    private Double shipton;
    private Integer shipempty;
    private Integer goodstype;
    private Double goodston;
    private Integer creater;
    private Date creattime;
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CLlCbllgc() {
    }

    /**
     * minimal constructor
     */
    public CLlCbllgc(Integer gcdid, Integer type, Date starttime,
                     Date endtime) {
        this.gcdid = gcdid;
        this.type = type;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public CLlCbllgc(Integer id, Integer gcdid, Integer type, Date starttime, Date endtime, String shipname, Integer shipdire, Integer shiptype, Double shipton, Integer shipempty, Integer goodstype, Double goodston, Integer creater, Date creattime, Date updatetime) {
        this.id = id;
        this.gcdid = gcdid;
        this.type = type;
        this.starttime = starttime;
        this.endtime = endtime;
        this.shipname = shipname;
        this.shipdire = shipdire;
        this.shiptype = shiptype;
        this.shipton = shipton;
        this.shipempty = shipempty;
        this.goodstype = goodstype;
        this.goodston = goodston;
        this.creater = creater;
        this.creattime = creattime;
        this.updatetime = updatetime;
    }

    public CLlCbllgc(Integer gcdid, Integer type, Date starttime, Date endtime, String shipname, Integer shipdire, Integer shiptype, Double shipton, Integer shipempty, Integer goodstype, Double goodston, Integer creater, Date creattime, Date updatetime) {

        this.gcdid = gcdid;
        this.type = type;
        this.starttime = starttime;
        this.endtime = endtime;
        this.shipname = shipname;
        this.shipdire = shipdire;
        this.shiptype = shiptype;
        this.shipton = shipton;
        this.shipempty = shipempty;
        this.goodstype = goodstype;
        this.goodston = goodston;
        this.creater = creater;
        this.creattime = creattime;
        this.updatetime = updatetime;
    }

    public Integer getShipdire() {
        return shipdire;
    }

    public void setShipdire(Integer shipdire) {
        this.shipdire = shipdire;
    }
// Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGcdid() {
        return this.gcdid;
    }

    public void setGcdid(Integer gcdid) {
        this.gcdid = gcdid;
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

    public String getShipname() {
        return this.shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public Integer getShiptype() {
        return this.shiptype;
    }

    public void setShiptype(Integer shiptype) {
        this.shiptype = shiptype;
    }

    public Double getShipton() {
        return this.shipton;
    }

    public void setShipton(Double shipton) {
        this.shipton = shipton;
    }

    public Integer getShipempty() {
        return this.shipempty;
    }

    public void setShipempty(Integer shipempty) {
        this.shipempty = shipempty;
    }

    public Integer getGoodstype() {
        return this.goodstype;
    }

    public void setGoodstype(Integer goodstype) {
        this.goodstype = goodstype;
    }

    public Double getGoodston() {
        return this.goodston;
    }

    public void setGoodston(Double goodston) {
        this.goodston = goodston;
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

}