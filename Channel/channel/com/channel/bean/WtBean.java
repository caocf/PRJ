package com.channel.bean;

import com.channel.model.xc.CXcSj;

import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
public class WtBean {
    private Integer wt;//问题分类
    private String wtsm;//问题分类说明
    private String clyj;//意见
    private String cljg;//结果
    private List<CXcSj> sjs;//具体事件

    public Integer getWt() {
        return wt;
    }

    public void setWt(Integer wt) {
        this.wt = wt;
    }

    public String getWtsm() {
        return wtsm;
    }

    public void setWtsm(String wtsm) {
        this.wtsm = wtsm;
    }

    public String getClyj() {
        return clyj;
    }

    public void setClyj(String clyj) {
        this.clyj = clyj;
    }

    public String getCljg() {
        return cljg;
    }

    public void setCljg(String cljg) {
        this.cljg = cljg;
    }

    public List<CXcSj> getSjs() {
        return sjs;
    }

    public void setSjs(List<CXcSj> sjs) {
        this.sjs = sjs;
    }
}
