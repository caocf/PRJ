package net.hxkg.report;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 2016/5/19.
 */
public class WharfDangerreportEN  implements Serializable
{
    private int id;
    private String ship;
    private String startport;
    private String targetport;
    private String goods;
    private String goodstype;
    private String goodsweight;
    private String portime;
    private String status;
    private String reason;
    private String endtime; 
    
    private String number;
    
    public int statusid;
    
    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public String getStartport() {
        return startport;
    }

    public void setStartport(String startport) {
        this.startport = startport;
    }

    public String getTargetport() {
        return targetport;
    }

    public void setTargetport(String targetport) {
        this.targetport = targetport;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    public String getGoodsweight() {
        return goodsweight;
    }

    public void setGoodsweight(String goodsweight) {
        this.goodsweight = goodsweight;
    }

    public String getPortime() {
        return portime;
    }

    public void setPortime(String portime) {
        this.portime = portime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WharfDangerreportEN that = (WharfDangerreportEN) o;

        if (id != that.id) return false;
        if (ship != null ? !ship.equals(that.ship) : that.ship != null) return false;
        if (startport != null ? !startport.equals(that.startport) : that.startport != null) return false;
        if (targetport != null ? !targetport.equals(that.targetport) : that.targetport != null) return false;
        if (goods != null ? !goods.equals(that.goods) : that.goods != null) return false;
        if (goodstype != null ? !goodstype.equals(that.goodstype) : that.goodstype != null) return false;
        if (goodsweight != null ? !goodsweight.equals(that.goodsweight) : that.goodsweight != null) return false;
        if (portime != null ? !portime.equals(that.portime) : that.portime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ship != null ? ship.hashCode() : 0);
        result = 31 * result + (startport != null ? startport.hashCode() : 0);
        result = 31 * result + (targetport != null ? targetport.hashCode() : 0);
        result = 31 * result + (goods != null ? goods.hashCode() : 0);
        result = 31 * result + (goodstype != null ? goodstype.hashCode() : 0);
        result = 31 * result + (goodsweight != null ? goodsweight.hashCode() : 0);
        result = 31 * result + (portime != null ? portime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
