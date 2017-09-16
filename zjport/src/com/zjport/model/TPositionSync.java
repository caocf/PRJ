package com.zjport.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Entity
@Table(name = "t_position_sync", schema = "zjport", catalog = "")
public class TPositionSync {
    private String stPositionSyncId;
    private String stPositionName;
    private String stOrgSyncId;
    private Integer stAddress;

    @Id
    @Column(name = "ST_POSITION_SYNC_ID")
    public String getStPositionSyncId() {
        return stPositionSyncId;
    }

    public void setStPositionSyncId(String stPositionSyncId) {
        this.stPositionSyncId = stPositionSyncId;
    }

    @Basic
    @Column(name = "ST_POSITION_NAME")
    public String getStPositionName() {
        return stPositionName;
    }

    public void setStPositionName(String stPositionName) {
        this.stPositionName = stPositionName;
    }

    @Basic
    @Column(name = "ST_ORG_SYNC_ID")
    public String getStOrgSyncId() {
        return stOrgSyncId;
    }

    public void setStOrgSyncId(String stOrgSyncId) {
        this.stOrgSyncId = stOrgSyncId;
    }

    @Basic
    @Column(name = "ST_ADDRESS")
    public Integer getStAddress() {
        return stAddress;
    }

    public void setStAddress(Integer stAddress) {
        this.stAddress = stAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TPositionSync that = (TPositionSync) o;

        if (stPositionSyncId != null ? !stPositionSyncId.equals(that.stPositionSyncId) : that.stPositionSyncId != null)
            return false;
        if (stPositionName != null ? !stPositionName.equals(that.stPositionName) : that.stPositionName != null)
            return false;
        if (stOrgSyncId != null ? !stOrgSyncId.equals(that.stOrgSyncId) : that.stOrgSyncId != null) return false;
        if (stAddress != null ? !stAddress.equals(that.stAddress) : that.stAddress != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stPositionSyncId != null ? stPositionSyncId.hashCode() : 0;
        result = 31 * result + (stPositionName != null ? stPositionName.hashCode() : 0);
        result = 31 * result + (stOrgSyncId != null ? stOrgSyncId.hashCode() : 0);
        result = 31 * result + (stAddress != null ? stAddress.hashCode() : 0);
        return result;
    }
}
