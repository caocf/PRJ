package com.zjport.model;

import javax.persistence.*;

/**
 * Created by TWQ on 2016/9/1.
 */
@Entity
@Table(name = "t_scan_department", schema = "zjport", catalog = "")
public class TScanDepartment {
    private int stScanDepartmentId;
    private String stScanDepartMiddleId;
    private String stDepartmentId;

    @Id
    @Column(name = "ST_SCAN_DEPARTMENT_ID")
    public int getStScanDepartmentId() {
        return stScanDepartmentId;
    }

    public void setStScanDepartmentId(int stScanDepartmentId) {
        this.stScanDepartmentId = stScanDepartmentId;
    }

    @Basic
    @Column(name = "ST_SCAN_DEPART_MIDDLE_ID")
    public String getStScanDepartMiddleId() {
        return stScanDepartMiddleId;
    }

    public void setStScanDepartMiddleId(String stScanDepartMiddleId) {
        this.stScanDepartMiddleId = stScanDepartMiddleId;
    }

    @Basic
    @Column(name = "ST_DEPARTMENT_ID")
    public String getStDepartmentId() {
        return stDepartmentId;
    }

    public void setStDepartmentId(String stDepartmentId) {
        this.stDepartmentId = stDepartmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TScanDepartment that = (TScanDepartment) o;

        if (stScanDepartmentId != that.stScanDepartmentId) return false;
        if (stScanDepartMiddleId != null ? !stScanDepartMiddleId.equals(that.stScanDepartMiddleId) : that.stScanDepartMiddleId != null)
            return false;
        if (stDepartmentId != null ? !stDepartmentId.equals(that.stDepartmentId) : that.stDepartmentId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stScanDepartmentId;
        result = 31 * result + (stScanDepartMiddleId != null ? stScanDepartMiddleId.hashCode() : 0);
        result = 31 * result + (stDepartmentId != null ? stDepartmentId.hashCode() : 0);
        return result;
    }
}
