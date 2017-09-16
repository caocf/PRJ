package net.hxkg.report;


import java.io.Serializable;
/**
 * Created by Admin on 2016/5/12.
 */
public class ShipDangerreportEN  implements Serializable
{
    private int id;
    private String start;
    private String berthtime;
    private String status;
    private String reason;
    String shipname;
    
    public int statusid;
    
    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) 
    {
        this.shipname = shipname;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBerthtime() {
        return berthtime;
    }

    public void setBerthtime(String berthtime) {
        this.berthtime = berthtime;
    }


    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    private String target;
    private String goods;
    private String rank;
    private String tons;
    private String unit;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    //private ShipEN shipEN;//many to one


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTons() {
        return tons;
    }

    public void setTons(String tons) {
        this.tons = tons;
    }
}
