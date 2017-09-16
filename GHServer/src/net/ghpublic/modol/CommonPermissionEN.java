package net.ghpublic.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by Admin on 2016/5/19.
 */
public class CommonPermissionEN {
    private int id;
    private String projectname;
    private String location;
    private String buildproperty;
    private String unit;
    private String operator;
    private String tel;
    private String designer;
    private String accept;
    private String permmitnum;
    private Date applytime;
    private String status;
    private PublicUserEN user;
    @JsonIgnore
    public PublicUserEN getUser() {
        return user;
    }

    public void setUser(PublicUserEN user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBuildproperty() {
        return buildproperty;
    }

    public void setBuildproperty(String buildproperty) {
        this.buildproperty = buildproperty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getPermmitnum() {
        return permmitnum;
    }

    public void setPermmitnum(String permmitnum) {
        this.permmitnum = permmitnum;
    }

    public Date getApplytime() {
        return applytime;
    }

    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonPermissionEN that = (CommonPermissionEN) o;

        if (id != that.id) return false;
        if (projectname != null ? !projectname.equals(that.projectname) : that.projectname != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (buildproperty != null ? !buildproperty.equals(that.buildproperty) : that.buildproperty != null)
            return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (operator != null ? !operator.equals(that.operator) : that.operator != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (designer != null ? !designer.equals(that.designer) : that.designer != null) return false;
        if (accept != null ? !accept.equals(that.accept) : that.accept != null) return false;
        if (permmitnum != null ? !permmitnum.equals(that.permmitnum) : that.permmitnum != null) return false;
        if (applytime != null ? !applytime.equals(that.applytime) : that.applytime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (projectname != null ? projectname.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (buildproperty != null ? buildproperty.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (designer != null ? designer.hashCode() : 0);
        result = 31 * result + (accept != null ? accept.hashCode() : 0);
        result = 31 * result + (permmitnum != null ? permmitnum.hashCode() : 0);
        result = 31 * result + (applytime != null ? applytime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
