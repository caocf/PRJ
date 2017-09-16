package com.zjport.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by TWQ on 2016/12/7.
 */
@Entity
@Table(name = "t_portalet", schema = "zjport", catalog = "")
public class TPortlet {
    private int stPortletId;
    private String stPortletPartMiddleId;
    private Integer stUserId;
    private Timestamp dtOperate;

    @Id
    @Column(name = "ST_PORTLET_ID")
    public int getStPortletId() {
        return stPortletId;
    }

    public void setStPortletId(int stPortletId) {
        this.stPortletId = stPortletId;
    }

    @Basic
    @Column(name = "ST_PORTLET_PART_MIDDLE_ID")
    public String getStPortletPartMiddleId() {
        return stPortletPartMiddleId;
    }

    public void setStPortletPartMiddleId(String stPortletPartMiddleId) {
        this.stPortletPartMiddleId = stPortletPartMiddleId;
    }

    @Basic
    @Column(name = "ST_USER_ID")
    public Integer getStUserId() {
        return stUserId;
    }

    public void setStUserId(Integer stUserId) {
        this.stUserId = stUserId;
    }

    @Basic
    @Column(name = "DT_OPERATE")
    public Timestamp getDtOperate() {
        return dtOperate;
    }

    public void setDtOperate(Timestamp dtOperate) {
        this.dtOperate = dtOperate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TPortlet portlet = (TPortlet) o;

        if (stPortletId != portlet.stPortletId) return false;
        if (stPortletPartMiddleId != null ? !stPortletPartMiddleId.equals(portlet.stPortletPartMiddleId) : portlet.stPortletPartMiddleId != null)
            return false;
        if (stUserId != null ? !stUserId.equals(portlet.stUserId) : portlet.stUserId != null) return false;
        if (dtOperate != null ? !dtOperate.equals(portlet.dtOperate) : portlet.dtOperate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stPortletId;
        result = 31 * result + (stPortletPartMiddleId != null ? stPortletPartMiddleId.hashCode() : 0);
        result = 31 * result + (stUserId != null ? stUserId.hashCode() : 0);
        result = 31 * result + (dtOperate != null ? dtOperate.hashCode() : 0);
        return result;
    }
}
