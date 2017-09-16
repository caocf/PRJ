package com.zjport.model;

import java.sql.Timestamp;

/**
 * Created by TWQ on 2016/10/9.
 */
public class TBacklog implements java.io.Serializable{
    private int stBacklogId;
    private String stBacklogName;
    private String stType;
    private Integer stRelationId;
    private Integer stUserId;
    private Boolean stState;
    private Timestamp dtCreate;
    private Timestamp dtDeal;

    public int getStBacklogId() {
        return stBacklogId;
    }

    public void setStBacklogId(int stBacklogId) {
        this.stBacklogId = stBacklogId;
    }

    public String getStBacklogName() {
        return stBacklogName;
    }

    public void setStBacklogName(String stBacklogName) {
        this.stBacklogName = stBacklogName;
    }

    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType;
    }

    public Integer getStRelationId() {
        return stRelationId;
    }

    public void setStRelationId(Integer stRelationId) {
        this.stRelationId = stRelationId;
    }

    public Integer getStUserId() {
        return stUserId;
    }

    public void setStUserId(Integer stUserId) {
        this.stUserId = stUserId;
    }

    public Boolean getStState() {
        return stState;
    }

    public void setStState(Boolean stState) {
        this.stState = stState;
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Timestamp getDtDeal() {
        return dtDeal;
    }

    public void setDtDeal(Timestamp dtDeal) {
        this.dtDeal = dtDeal;
    }
}
