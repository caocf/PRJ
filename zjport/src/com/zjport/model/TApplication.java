package com.zjport.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Entity
@Table(name = "t_application", schema = "zjport", catalog = "")
public class TApplication {
    private int stApplicationId;
    private String stAppName;
    private String stAppUrl;
    private String stAppImgUrl;

    @Id
    @Column(name = "ST_APPLICATION_ID")
    public int getStApplicationId() {
        return stApplicationId;
    }

    public void setStApplicationId(int stApplicationId) {
        this.stApplicationId = stApplicationId;
    }

    @Basic
    @Column(name = "ST_APP_NAME")
    public String getStAppName() {
        return stAppName;
    }

    public void setStAppName(String stAppName) {
        this.stAppName = stAppName;
    }

    @Basic
    @Column(name = "ST_APP_URL")
    public String getStAppUrl() {
        return stAppUrl;
    }

    public void setStAppUrl(String stAppUrl) {
        this.stAppUrl = stAppUrl;
    }

    @Basic
    @Column(name = "ST_APP_IMG_URL")
    public String getStAppImgUrl() {
        return stAppImgUrl;
    }

    public void setStAppImgUrl(String stAppImgUrl) {
        this.stAppImgUrl = stAppImgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TApplication that = (TApplication) o;

        if (stApplicationId != that.stApplicationId) return false;
        if (stAppName != null ? !stAppName.equals(that.stAppName) : that.stAppName != null) return false;
        if (stAppUrl != null ? !stAppUrl.equals(that.stAppUrl) : that.stAppUrl != null) return false;
        if (stAppImgUrl != null ? !stAppImgUrl.equals(that.stAppImgUrl) : that.stAppImgUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stApplicationId;
        result = 31 * result + (stAppName != null ? stAppName.hashCode() : 0);
        result = 31 * result + (stAppUrl != null ? stAppUrl.hashCode() : 0);
        result = 31 * result + (stAppImgUrl != null ? stAppImgUrl.hashCode() : 0);
        return result;
    }
}
