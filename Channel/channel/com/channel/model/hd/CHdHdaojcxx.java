package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdHdaojcxx entity. @author MyEclipse Persistence Tools
 */

public class CHdHdaojcxx implements java.io.Serializable, Comparable<CHdHdaojcxx> {

    // Fields

    private Integer id;

    @UiInputValidator({@Validator(filter = "notempty"), @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 0, order = 0, groupname = "航道基础信息", must = true)
    @PropDesc("航道编号")
    private String hdbh;

    @UiInputValidator({@Validator(filter = "notempty"), @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 0, order = 0, must = true)
    @PropDesc("航道名称")
    private String hdmc;

    @UiInput(group = 0, order = 0, inputtype = "selectyesno")
    @PropDesc("是否全省航道")
    private Integer sfqshd;

    @UiInput(group = 0, order = 0, inputtype = "selectdict", selectdictname = "sssjbh")
    @PropDesc("所属市级编号")
    private Integer sssjbh;

    @UiInput(group = 0, order = 0, inputtype = "selectyesno")
    @PropDesc("是否季节性航道")
    private Integer sfjjx;

    @UiInput(group = 0, order = 0, inputtype = "selectyesno", defaultvalfromweb = "sfgg", readonly = true)
    @PropDesc("是否骨干航道")
    private Integer sfgg;

    @UiInputValidator({@Validator(filter = "length", min = 0, max = 255)})
    @UiInput(group = 2, order = 5, oneline = true, inputtype = "textarea")
    @PropDesc("备注")
    private String bz;

    @PropDesc("创建时间")
    private Date createtime;
    @PropDesc("更新时间")
    private Date updatetime;


    private int hduancnt;
    private String hduansjbh;

    public int getHduancnt() {
        return hduancnt;
    }

    public void setHduancnt(int hduancnt) {
        this.hduancnt = hduancnt;
    }

    public String getHduansjbh() {
        return hduansjbh;
    }

    public void setHduansjbh(String hduansjbh) {
        this.hduansjbh = hduansjbh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHdbh() {
        return hdbh;
    }

    public void setHdbh(String hdbh) {
        this.hdbh = hdbh;
    }

    public String getHdmc() {
        return hdmc;
    }

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }

    public Integer getSfqshd() {
        return sfqshd;
    }

    public void setSfqshd(Integer sfqshd) {
        this.sfqshd = sfqshd;
    }

    public Integer getSssjbh() {
        return sssjbh;
    }

    public void setSssjbh(Integer sssjbh) {
        this.sssjbh = sssjbh;
    }

    public Integer getSfjjx() {
        return sfjjx;
    }

    public void setSfjjx(Integer sfjjx) {
        this.sfjjx = sfjjx;
    }

    public Integer getSfgg() {
        return sfgg;
    }

    public void setSfgg(Integer sfgg) {
        this.sfgg = sfgg;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public CHdHdaojcxx() {
    }

    public CHdHdaojcxx(String hdbh, String hdmc, Integer sfqshd, Integer sssjbh, Integer sfjjx, Integer sfgg, String bz, Date createtime, Date updatetime) {
        this.hdbh = hdbh;
        this.hdmc = hdmc;
        this.sfqshd = sfqshd;
        this.sssjbh = sssjbh;
        this.sfjjx = sfjjx;
        this.sfgg = sfgg;
        this.bz = bz;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdHdaojcxx(Date updatetime, Integer id, String hdbh, String hdmc, Integer sfqshd, Integer sssjbh, Integer sfjjx, Integer sfgg, String bz, Date createtime) {
        this.updatetime = updatetime;
        this.id = id;
        this.hdbh = hdbh;
        this.hdmc = hdmc;
        this.sfqshd = sfqshd;
        this.sssjbh = sssjbh;
        this.sfjjx = sfjjx;
        this.sfgg = sfgg;
        this.bz = bz;
        this.createtime = createtime;
    }

    @Override
    public int compareTo(CHdHdaojcxx hd) {
        int val= this.getSfgg()-hd.getSfgg();
        if (val!=0){
            return val;
        }
        return  this.getCreatetime().compareTo(hd.getCreatetime());
    }
}