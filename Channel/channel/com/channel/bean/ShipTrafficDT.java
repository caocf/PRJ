package com.channel.bean;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class ShipTrafficDT implements Serializable, Comparable<ShipTrafficDT> {
    private Integer id;
    private Integer gcdid;
    private Integer type;
    private String mc;
    private Date starttime;
    private Date endtime;
    private String shipname;
    private Integer shipdire;
    private String shiptype;
    private Double shipton;
    private Integer shipempty;
    private String goodstype;
    private Double goodston;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGcdid() {
        return gcdid;
    }

    public void setGcdid(Integer gcdid) {
        this.gcdid = gcdid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
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

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public Integer getShipdire() {
        return shipdire;
    }

    public void setShipdire(Integer shipdire) {
        this.shipdire = shipdire;
    }

    public String getShiptype() {
        return shiptype;
    }

    public void setShiptype(String shiptype) {
        this.shiptype = shiptype;
    }

    public Double getShipton() {
        return shipton;
    }

    public void setShipton(Double shipton) {
        this.shipton = shipton;
    }

    public Integer getShipempty() {
        return shipempty;
    }

    public void setShipempty(Integer shipempty) {
        this.shipempty = shipempty;
    }

    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    public Double getGoodston() {
        return goodston;
    }

    public void setGoodston(Double goodston) {
        this.goodston = goodston;
    }

    public ShipTrafficDT() {
    }

    public ShipTrafficDT(Integer gcdid, Integer type, String mc, Date starttime, Date endtime, String shipname, Integer shipdire, String shiptype, Double shipton, Integer shipempty, String goodstype, Double goodston) {

        this.gcdid = gcdid;
        this.type = type;
        this.mc = mc;
        this.starttime = starttime;
        this.endtime = endtime;
        this.shipname = shipname;
        this.shipdire = shipdire;
        this.shiptype = shiptype;
        this.shipton = shipton;
        this.shipempty = shipempty;
        this.goodstype = goodstype;
        this.goodston = goodston;
    }

    public ShipTrafficDT(Integer id, Integer gcdid, Integer type, String mc, Date starttime, Date endtime, String shipname, Integer shipdire, String shiptype, Double shipton, Integer shipempty, String goodstype, Double goodston) {

        this.id = id;
        this.gcdid = gcdid;
        this.type = type;
        this.mc = mc;
        this.starttime = starttime;
        this.endtime = endtime;
        this.shipname = shipname;
        this.shipdire = shipdire;
        this.shiptype = shiptype;
        this.shipton = shipton;
        this.shipempty = shipempty;
        this.goodstype = goodstype;
        this.goodston = goodston;
    }

    @Override
    public int compareTo(ShipTrafficDT o) {
        if (this.getStarttime().before(o.getStarttime())) {
            return 1;
        }
        if (this.getStarttime().after(o.getStarttime())) {
            return -1;
        }
        return 0;
    }
}
