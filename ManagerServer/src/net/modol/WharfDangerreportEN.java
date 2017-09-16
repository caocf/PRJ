package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by Admin on 2016/5/19.
 */
public class WharfDangerreportEN {
    private int id;
    private String ship;
    String workway;

    public String getWorkway() {
        return workway;
    }

    public void setWorkway(String workway) {
        this.workway = workway;
    }

    private String goodsname;
    private CommonDangerrankEN rank;
    private String mount;
    private String portime;
    private String wharfEN;
    private String reason;
    private String number;
    private String endtime;
    private CommonGoodsunitEN unit;
    private CommonPortEN startport;
    private CommonPortEN targetport;
    private ShipStatusEN status;
    String checker;

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public CommonDangerrankEN getRank() {
        return rank;
    }

    public void setRank(CommonDangerrankEN rank) {
        this.rank = rank;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public String getWharfEN() {
        return wharfEN;
    }

    public void setWharfEN(String wharfEN) {
        this.wharfEN = wharfEN;
    }

    public CommonGoodsunitEN getUnit() {
        return unit;
    }

    public void setUnit(CommonGoodsunitEN unit) {
        this.unit = unit;
    }

    public CommonPortEN getStartport() {
        return startport;
    }

    public void setStartport(CommonPortEN startport) {
        this.startport = startport;
    }

    public CommonPortEN getTargetport() {
        return targetport;
    }

    public void setTargetport(CommonPortEN targetport) {
        this.targetport = targetport;
    }

    public ShipStatusEN getStatus() {
        return status;
    }

    public void setStatus(ShipStatusEN status) {
        this.status = status;
    }

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




    public String getPortime() {
        return portime;
    }

    public void setPortime(String portime) {
        this.portime = portime;
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
        result = 31 * result + (portime != null ? portime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
