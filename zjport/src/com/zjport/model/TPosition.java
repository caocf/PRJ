package com.zjport.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Entity
@Table(name = "t_position", schema = "zjport", catalog = "")
public class TPosition {
    private int stPositionId;
    private String stPositionName;

    @Id
    @Column(name = "ST_POSITION_ID")
    public int getStPositionId() {
        return stPositionId;
    }

    public void setStPositionId(int stPositionId) {
        this.stPositionId = stPositionId;
    }

    @Basic
    @Column(name = "ST_POSITION_NAME")
    public String getStPositionName() {
        return stPositionName;
    }

    public void setStPositionName(String stPositionName) {
        this.stPositionName = stPositionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TPosition tPosition = (TPosition) o;

        if (stPositionId != tPosition.stPositionId) return false;
        if (stPositionName != null ? !stPositionName.equals(tPosition.stPositionName) : tPosition.stPositionName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stPositionId;
        result = 31 * result + (stPositionName != null ? stPositionName.hashCode() : 0);
        return result;
    }
}
