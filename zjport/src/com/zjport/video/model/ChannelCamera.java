package com.zjport.video.model;

/**
 * Created by Will on 2016/10/18 11:24.
 */
public class ChannelCamera {
    private Integer id;
    private Integer hdid;
    private Integer cid;
    private Integer ctype;
    private Double lon;
    private Double lat;
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHdid() {
        return hdid;
    }

    public void setHdid(Integer hdid) {
        this.hdid = hdid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ChannelCamera() {
    }

    public ChannelCamera(Integer id, Integer hdid, Integer cid, Integer ctype, Double lon, Double lat) {
        this.id = id;
        this.hdid = hdid;
        this.cid = cid;
        this.ctype = ctype;
        this.lon = lon;
        this.lat = lat;
    }

    public ChannelCamera(Integer id, Integer hdid, Integer cid, Integer ctype) {
        this.id = id;
        this.hdid = hdid;
        this.cid = cid;
        this.ctype = ctype;
    }
}
