package net.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/5/12.
 */
public class ShipDangerreportEN {
    private int id;
    private String start;
    private String shipname;//many to one
    String checker;
    Date committime;

    public Date getCommittime() {
        return committime;
    }

    public void setCommittime(Date committime) {
        this.committime = committime;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public ShipStatusEN getStatus() {
        return status;
    }

    public void setStatus(ShipStatusEN status) {
        this.status = status;
    }

    public CommonDangerrankEN getDangerrankEN() {
        return dangerrankEN;
    }

    public void setDangerrankEN(CommonDangerrankEN dangerrankEN) {
        this.dangerrankEN = dangerrankEN;
    }

    public CommonGoodsunitEN getGoodsunitEN() {
        return goodsunitEN;
    }

    public void setGoodsunitEN(CommonGoodsunitEN goodsunitEN) {
        this.goodsunitEN = goodsunitEN;
    }

    public CommonPortEN getStartportEN() {
        return startportEN;
    }

    public void setStartportEN(CommonPortEN startportEN) {
        this.startportEN = startportEN;
    }

    public CommonPortEN getEndportEN() {
        return endportEN;
    }

    public void setEndportEN(CommonPortEN endportEN) {
        this.endportEN = endportEN;
    }

    private String berthtime;
    private ShipStatusEN status;
    private String reason;
    CommonDangerrankEN dangerrankEN;
    CommonGoodsunitEN goodsunitEN;
    CommonPortEN startportEN;
    CommonPortEN endportEN;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public String getBerthtime() {
        return berthtime;
    }

    public void setBerthtime(String berthtime) {
        this.berthtime = berthtime;
    }


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    private String goods;

    private String tons;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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


    public String getTons() {
        return tons;
    }

    public void setTons(String tons) {
        this.tons = tons;
    }
}
