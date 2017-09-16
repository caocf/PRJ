package com.zjport.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Entity
@Table(name = "t_information_target", schema = "zjport", catalog = "")
public class TInformationTarget {
    private int stInformTargetId;
    private String stInformTargetGroupName;
    private Integer stUserId;
    private Integer stCreateUserId;

    @Id
    @Column(name = "ST_INFORM_TARGET_ID")
    public int getStInformTargetId() {
        return stInformTargetId;
    }

    public void setStInformTargetId(int stInformTargetId) {
        this.stInformTargetId = stInformTargetId;
    }

    @Basic
    @Column(name = "ST_INFORM_TARGET_GROUP_NAME")
    public String getStInformTargetGroupName() {
        return stInformTargetGroupName;
    }

    public void setStInformTargetGroupName(String stInformTargetGroupName) {
        this.stInformTargetGroupName = stInformTargetGroupName;
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
    @Column(name = "ST_CREATE_USER_ID")
    public Integer getStCreateUserId() {
        return stCreateUserId;
    }

    public void setStCreateUserId(Integer stCreateUserId) {
        this.stCreateUserId = stCreateUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TInformationTarget that = (TInformationTarget) o;

        if (stInformTargetId != that.stInformTargetId) return false;
        if (stInformTargetGroupName != null ? !stInformTargetGroupName.equals(that.stInformTargetGroupName) : that.stInformTargetGroupName != null)
            return false;
        if (stUserId != null ? !stUserId.equals(that.stUserId) : that.stUserId != null) return false;
        if (stCreateUserId != null ? !stCreateUserId.equals(that.stCreateUserId) : that.stCreateUserId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stInformTargetId;
        result = 31 * result + (stInformTargetGroupName != null ? stInformTargetGroupName.hashCode() : 0);
        result = 31 * result + (stUserId != null ? stUserId.hashCode() : 0);
        result = 31 * result + (stCreateUserId != null ? stCreateUserId.hashCode() : 0);
        return result;
    }
}
