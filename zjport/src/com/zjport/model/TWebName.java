package com.zjport.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Entity
@Table(name = "t_web_name", schema = "zjport", catalog = "")
public class TWebName {
    private int stWebNameId;
    private String stName;
    private String stUrl;
    private Boolean stIsValid;

    @Id
    @Column(name = "ST_WEB_NAME_ID")
    public int getStWebNameId() {
        return stWebNameId;
    }

    public void setStWebNameId(int stWebNameId) {
        this.stWebNameId = stWebNameId;
    }

    @Basic
    @Column(name = "ST_NAME")
    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    @Basic
    @Column(name = "ST_URL")
    public String getStUrl() {
        return stUrl;
    }

    public void setStUrl(String stUrl) {
        this.stUrl = stUrl;
    }

    @Basic
    @Column(name = "ST_IS_VALID")
    public Boolean getStIsValid() {
        return stIsValid;
    }

    public void setStIsValid(Boolean stIsValid) {
        this.stIsValid = stIsValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TWebName tWebName = (TWebName) o;

        if (stWebNameId != tWebName.stWebNameId) return false;
        if (stName != null ? !stName.equals(tWebName.stName) : tWebName.stName != null) return false;
        if (stUrl != null ? !stUrl.equals(tWebName.stUrl) : tWebName.stUrl != null) return false;
        if (stIsValid != null ? !stIsValid.equals(tWebName.stIsValid) : tWebName.stIsValid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stWebNameId;
        result = 31 * result + (stName != null ? stName.hashCode() : 0);
        result = 31 * result + (stUrl != null ? stUrl.hashCode() : 0);
        result = 31 * result + (stIsValid != null ? stIsValid.hashCode() : 0);
        return result;
    }
}
