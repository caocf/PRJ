package com.zjport.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Entity
@Table(name = "t_web_column", schema = "zjport", catalog = "")
public class TWebColumn {
    private int stWebColumnId;
    private Integer stWebNameId;
    private String stWebColumnName;
    private Boolean stIsValid;

    @Id
    @Column(name = "ST_WEB_COLUMN_ID")
    public int getStWebColumnId() {
        return stWebColumnId;
    }

    public void setStWebColumnId(int stWebColumnId) {
        this.stWebColumnId = stWebColumnId;
    }

    @Basic
    @Column(name = "ST_WEB_NAME_ID")
    public Integer getStWebNameId() {
        return stWebNameId;
    }

    public void setStWebNameId(Integer stWebNameId) {
        this.stWebNameId = stWebNameId;
    }

    @Basic
    @Column(name = "ST_WEB_COLUMN_NAME")
    public String getStWebColumnName() {
        return stWebColumnName;
    }

    public void setStWebColumnName(String stWebColumnName) {
        this.stWebColumnName = stWebColumnName;
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

        TWebColumn that = (TWebColumn) o;

        if (stWebColumnId != that.stWebColumnId) return false;
        if (stWebNameId != null ? !stWebNameId.equals(that.stWebNameId) : that.stWebNameId != null) return false;
        if (stWebColumnName != null ? !stWebColumnName.equals(that.stWebColumnName) : that.stWebColumnName != null)
            return false;
        if (stIsValid != null ? !stIsValid.equals(that.stIsValid) : that.stIsValid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stWebColumnId;
        result = 31 * result + (stWebNameId != null ? stWebNameId.hashCode() : 0);
        result = 31 * result + (stWebColumnName != null ? stWebColumnName.hashCode() : 0);
        result = 31 * result + (stIsValid != null ? stIsValid.hashCode() : 0);
        return result;
    }
}
