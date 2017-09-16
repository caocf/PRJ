package com.zjport.model;

import java.sql.Timestamp;

/**
 * Created by TWQ on 2016/12/7.
 */
public class TPortletLib {
    private int stPortletLibId;
    private String stModuleName;
    private String stModuleId;
    private String stIcon;
    private String stUrl;
    private String stDescribe;
    private Integer stOrder;
    private String stUserName;
    private Timestamp dtCreate;
    private String stType;

    public int getStPortletLibId() {
        return stPortletLibId;
    }

    public void setStPortletLibId(int stPortletLibId) {
        this.stPortletLibId = stPortletLibId;
    }

    public String getStModuleName() {
        return stModuleName;
    }

    public void setStModuleName(String stModuleName) {
        this.stModuleName = stModuleName;
    }

    public String getStModuleId() {
        return stModuleId;
    }

    public void setStModuleId(String stModuleId) {
        this.stModuleId = stModuleId;
    }

    public String getStIcon() {
        return stIcon;
    }

    public void setStIcon(String stIcon) {
        this.stIcon = stIcon;
    }

    public String getStUrl() {
        return stUrl;
    }

    public void setStUrl(String stUrl) {
        this.stUrl = stUrl;
    }

    public String getStDescribe() {
        return stDescribe;
    }

    public void setStDescribe(String stDescribe) {
        this.stDescribe = stDescribe;
    }

    public Integer getStOrder() {
        return stOrder;
    }

    public void setStOrder(Integer stOrder) {
        this.stOrder = stOrder;
    }

    public String getStUserName() {
        return stUserName;
    }

    public void setStUserName(String stUserName) {
        this.stUserName = stUserName;
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
    }

    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType;
    }
}
