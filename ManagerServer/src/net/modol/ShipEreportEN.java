package net.modol;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 2016/11/2.
 */
public class ShipEreportEN
{
    private int id;
    private String shipname;
    private String goodscount;
    private String lastfuelcount;
    Date porttime;
    Date lastfueltime;
    ShipPortregionEN portregionEN;//港区
    ShipReporttypeEN reporttypeEN;//进出港
    ShipReportclassEN reportclassEN;//报告类型
    CommonPortEN startport;//始发港
    CommonPortEN endport;//目的港
    CommonGoodsunitEN goodsunitEN;//货物单位
    Set<ShipEreporteditEN> shipEreporteditENSet=new HashSet<>();

    public Set<ShipEreporteditEN> getShipEreporteditENSet() {
        return shipEreporteditENSet;
    }

    public void setShipEreporteditENSet(Set<ShipEreporteditEN> shipEreporteditENSet) {
        this.shipEreporteditENSet = shipEreporteditENSet;
    }

    Date commitdate;

    public Date getCommitdate() {
        return commitdate;
    }

    public void setCommitdate(Date commitdate) {
        this.commitdate = commitdate;
    }

    public CommonGoodstypeEN getGoodstypeEN() {
        return goodstypeEN;
    }

    public void setGoodstypeEN(CommonGoodstypeEN goodstypeEN) {
        this.goodstypeEN = goodstypeEN;
    }

    public CommonPortEN getEndport() {
        return endport;
    }

    public void setEndport(CommonPortEN endport) {
        this.endport = endport;
    }

    public CommonGoodsunitEN getGoodsunitEN() {
        return goodsunitEN;
    }

    public void setGoodsunitEN(CommonGoodsunitEN goodsunitEN) {
        this.goodsunitEN = goodsunitEN;
    }

    public CommonPortEN getStartport() {
        return startport;
    }

    public void setStartport(CommonPortEN startport) {
        this.startport = startport;
    }

    CommonGoodstypeEN goodstypeEN;//货物种类

    public ShipStatusEN getStatusEN() {
        return statusEN;
    }

    public void setStatusEN(ShipStatusEN statusEN) {
        this.statusEN = statusEN;
    }

    ShipStatusEN statusEN;//报告单状态

    public ShipReportclassEN getReportclassEN() {
        return reportclassEN;
    }

    public void setReportclassEN(ShipReportclassEN reportclassEN) {
        this.reportclassEN = reportclassEN;
    }

    public ShipPortregionEN getPortregionEN() {
        return portregionEN;
    }

    public void setPortregionEN(ShipPortregionEN portregionEN) {
        this.portregionEN = portregionEN;
    }

    public ShipReporttypeEN getReporttypeEN() {
        return reporttypeEN;
    }

    public void setReporttypeEN(ShipReporttypeEN reporttypeEN) {
        this.reporttypeEN = reporttypeEN;
    }

    public Date getLastfueltime() {
        return lastfueltime;
    }

    public void setLastfueltime(Date lastfueltime) {
        this.lastfueltime = lastfueltime;
    }

    public Date getPorttime() {
        return porttime;
    }

    public void setPorttime(Date porttime) {
        this.porttime = porttime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public String getGoodscount() {
        return goodscount;
    }

    public void setGoodscount(String goodscount) {
        this.goodscount = goodscount;
    }

    public String getLastfuelcount() {
        return lastfuelcount;
    }

    public void setLastfuelcount(String lastfuelcount) {
        this.lastfuelcount = lastfuelcount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipEreportEN that = (ShipEreportEN) o;

        if (id != that.id) return false;
        if (shipname != null ? !shipname.equals(that.shipname) : that.shipname != null) return false;
        if (goodscount != null ? !goodscount.equals(that.goodscount) : that.goodscount != null) return false;
        if (lastfuelcount != null ? !lastfuelcount.equals(that.lastfuelcount) : that.lastfuelcount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (shipname != null ? shipname.hashCode() : 0);
        result = 31 * result + (goodscount != null ? goodscount.hashCode() : 0);
        result = 31 * result + (lastfuelcount != null ? lastfuelcount.hashCode() : 0);
        return result;
    }
}
