package com.zjport.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Entity
@Table(name = "t_user_application", schema = "zjport", catalog = "")
public class TUserApplication {
    private int stUserApplicationId;
    private Integer stAppId;
    private Integer stUserId;

    @Id
    @Column(name = "ST_USER_APPLICATION_ID")
    public int getStUserApplicationId() {
        return stUserApplicationId;
    }

    public void setStUserApplicationId(int stUserApplicationId) {
        this.stUserApplicationId = stUserApplicationId;
    }

    @Basic
    @Column(name = "ST_APP_ID")
    public Integer getStAppId() {
        return stAppId;
    }

    public void setStAppId(Integer stAppId) {
        this.stAppId = stAppId;
    }

    @Basic
    @Column(name = "ST_USER_ID")
    public Integer getStUserId() {
        return stUserId;
    }

    public void setStUserId(Integer stUserId) {
        this.stUserId = stUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TUserApplication that = (TUserApplication) o;

        if (stUserApplicationId != that.stUserApplicationId) return false;
        if (stAppId != null ? !stAppId.equals(that.stAppId) : that.stAppId != null) return false;
        if (stUserId != null ? !stUserId.equals(that.stUserId) : that.stUserId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stUserApplicationId;
        result = 31 * result + (stAppId != null ? stAppId.hashCode() : 0);
        result = 31 * result + (stUserId != null ? stUserId.hashCode() : 0);
        return result;
    }
}
