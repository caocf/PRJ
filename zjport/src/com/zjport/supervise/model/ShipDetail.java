package com.zjport.supervise.model;

import java.sql.Timestamp;

public class ShipDetail implements Comparable{
    private int id;
    private String shipname;// 船舶名称
    private double longitude;// 经度
    private double latitude;// 纬度
    private double speed;// 速度
    private double cruisedirection;// 航向
    private double shipdirection;// 航向
    private Timestamp shipdate;// 事件
    private String ais;
    private Timestamp adddate;

    private String area;

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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCruisedirection() {
        return cruisedirection;
    }

    public void setCruisedirection(double cruisedirection) {
        this.cruisedirection = cruisedirection;
    }

    public double getShipdirection() {
        return shipdirection;
    }

    public void setShipdirection(double shipdirection) {
        this.shipdirection = shipdirection;
    }

    public String getAis() {
        return ais;
    }

    public void setAis(String ais) {
        this.ais = ais;
    }

    public Timestamp getShipdate() {
        return shipdate;
    }

    public void setShipdate(Timestamp shipdate) {
        this.shipdate = shipdate;
    }

    public Timestamp getAdddate() {
        return adddate;
    }

    public void setAdddate(Timestamp adddate) {
        this.adddate = adddate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ShipDetail() {
        super();
    }

    public ShipDetail(String shipname, double longitude, double latitude, double speed, double cruisedirection, double shipdirection, Timestamp shipdate, String ais, Timestamp adddate, String area) {
        this.shipname = shipname;
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
        this.cruisedirection = cruisedirection;
        this.shipdirection = shipdirection;
        this.shipdate = shipdate;
        this.ais = ais;
        this.adddate = adddate;
        this.area = area;
    }

    @Override
    public int compareTo(Object o) {
        ShipDetail s = (ShipDetail) o;
        if (this.shipdate.getTime() < s.getShipdate().getTime())
        {
            return -1;
        }
        else if (this.shipdate.getTime() == s.getShipdate().getTime())
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
