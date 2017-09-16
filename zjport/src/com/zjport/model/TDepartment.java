package com.zjport.model;

import javax.persistence.*;

/**
 * Created by TWQ on 2016/9/1.
 */
@Entity
@Table(name = "t_department", schema = "zjport", catalog = "")
public class TDepartment {
    private String stDepartmentId;
    private String stDepartmentName;
    private String stOrgId;
    private String stKeyman;
    private String stContactMan;
    private String stContactTel;

    @Id
    @Column(name = "ST_DEPARTMENT_ID")
    public String getStDepartmentId() {
        return stDepartmentId;
    }

    public void setStDepartmentId(String stDepartmentId) {
        this.stDepartmentId = stDepartmentId;
    }

    @Basic
    @Column(name = "ST_DEPARTMENT_NAME")
    public String getStDepartmentName() {
        return stDepartmentName;
    }

    public void setStDepartmentName(String stDepartmentName) {
        this.stDepartmentName = stDepartmentName;
    }

    @Basic
    @Column(name = "ST_ORG_ID")
    public String getStOrgId() {
        return stOrgId;
    }

    public void setStOrgId(String stOrgId) {
        this.stOrgId = stOrgId;
    }

    @Basic
    @Column(name = "ST_KEYMAN")
    public String getStKeyman() {
        return stKeyman;
    }

    public void setStKeyman(String stKeyman) {
        this.stKeyman = stKeyman;
    }

    @Basic
    @Column(name = "ST_CONTACT_MAN")
    public String getStContactMan() {
        return stContactMan;
    }

    public void setStContactMan(String stContactMan) {
        this.stContactMan = stContactMan;
    }

    @Basic
    @Column(name = "ST_CONTACT_TEL")
    public String getStContactTel() {
        return stContactTel;
    }

    public void setStContactTel(String stContactTel) {
        this.stContactTel = stContactTel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TDepartment that = (TDepartment) o;

        if (stDepartmentId != null ? !stDepartmentId.equals(that.stDepartmentId) : that.stDepartmentId != null)
            return false;
        if (stDepartmentName != null ? !stDepartmentName.equals(that.stDepartmentName) : that.stDepartmentName != null)
            return false;
        if (stOrgId != null ? !stOrgId.equals(that.stOrgId) : that.stOrgId != null) return false;
        if (stKeyman != null ? !stKeyman.equals(that.stKeyman) : that.stKeyman != null) return false;
        if (stContactMan != null ? !stContactMan.equals(that.stContactMan) : that.stContactMan != null) return false;
        if (stContactTel != null ? !stContactTel.equals(that.stContactTel) : that.stContactTel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stDepartmentId != null ? stDepartmentId.hashCode() : 0;
        result = 31 * result + (stDepartmentName != null ? stDepartmentName.hashCode() : 0);
        result = 31 * result + (stOrgId != null ? stOrgId.hashCode() : 0);
        result = 31 * result + (stKeyman != null ? stKeyman.hashCode() : 0);
        result = 31 * result + (stContactMan != null ? stContactMan.hashCode() : 0);
        result = 31 * result + (stContactTel != null ? stContactTel.hashCode() : 0);
        return result;
    }
}
