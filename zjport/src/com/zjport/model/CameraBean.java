package com.zjport.model;

import com.zjport.util.Value;
import com.zjport.util.XMLObject;
import com.zjport.util.XMLUtils;

import java.util.List;

/**
 * Created by Will on 2016/10/13 13:09.
 */
public class CameraBean {
    private Integer id;
    private Integer sshdid;
    private String hdqdmc;
    private String hdzdmc;
    private Integer cid;
    private Integer ctype;
    private String cname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSshdid() {
        return sshdid;
    }

    public void setSshdid(Integer sshdid) {
        this.sshdid = sshdid;
    }

    public String getHdqdmc() {
        return hdqdmc;
    }

    public void setHdqdmc(String hdqdmc) {
        this.hdqdmc = hdqdmc;
    }

    public String getHdzdmc() {
        return hdzdmc;
    }

    public void setHdzdmc(String hdzdmc) {
        this.hdzdmc = hdzdmc;
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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public CameraBean() {

    }

}
