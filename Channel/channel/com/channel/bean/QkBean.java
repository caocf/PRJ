package com.channel.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
public class QkBean {
    private Integer qk;//情况分类
    private String qksm;//情况分类说明
    private List<WtBean> wts;//描述

    public Integer getQk() {
        return qk;
    }

    public void setQk(Integer qk) {
        this.qk = qk;
    }

    public String getQksm() {
        return qksm;
    }

    public void setQksm(String qksm) {
        this.qksm = qksm;
    }

    public List<WtBean> getWts() {
        return wts;
    }

    public void setWts(List<WtBean> wts) {
        this.wts = wts;
    }
}
