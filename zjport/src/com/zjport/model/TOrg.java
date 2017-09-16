package com.zjport.model;

import javax.persistence.*;

/**
 * Created by TWQ on 2016/9/1.
 */
@Entity
@Table(name = "t_org", schema = "zjport", catalog = "")
public class TOrg {
    private String stOrgId;
    private String stOrgName;
    private String stOrgCode;
    private String stOrgDomain;
    private String stParentOrgId;
    private String stOrgShortName;
    private String stOrgArea;
    private String stOrgAddress;
    private String stOrgPhone;
    private String stOrgPost;
    private String stOrgFox;

    @Id
    @Column(name = "ST_ORG_ID")
    public String getStOrgId() {
        return stOrgId;
    }

    public void setStOrgId(String stOrgId) {
        this.stOrgId = stOrgId;
    }

    @Basic
    @Column(name = "ST_ORG_NAME")
    public String getStOrgName() {
        return stOrgName;
    }

    public void setStOrgName(String stOrgName) {
        this.stOrgName = stOrgName;
    }

    @Basic
    @Column(name = "ST_ORG_CODE")
    public String getStOrgCode() {
        return stOrgCode;
    }

    public void setStOrgCode(String stOrgCode) {
        this.stOrgCode = stOrgCode;
    }

    @Basic
    @Column(name = "ST_ORG_DOMAIN")
    public String getStOrgDomain() {
        return stOrgDomain;
    }

    public void setStOrgDomain(String stOrgDomain) {
        this.stOrgDomain = stOrgDomain;
    }

    @Basic
    @Column(name = "ST_PARENT_ORG_ID")
    public String getStParentOrgId() {
        return stParentOrgId;
    }

    public void setStParentOrgId(String stParentOrgId) {
        this.stParentOrgId = stParentOrgId;
    }

    @Basic
    @Column(name = "ST_ORG_SHORT_NAME")
    public String getStOrgShortName() {
        return stOrgShortName;
    }

    public void setStOrgShortName(String stOrgShortName) {
        this.stOrgShortName = stOrgShortName;
    }

    @Basic
    @Column(name = "ST_ORG_AREA")
    public String getStOrgArea() {
        return stOrgArea;
    }

    public void setStOrgArea(String stOrgArea) {
        this.stOrgArea = stOrgArea;
    }

    @Basic
    @Column(name = "ST_ORG_ADDRESS")
    public String getStOrgAddress() {
        return stOrgAddress;
    }

    public void setStOrgAddress(String stOrgAddress) {
        this.stOrgAddress = stOrgAddress;
    }

    @Basic
    @Column(name = "ST_ORG_PHONE")
    public String getStOrgPhone() {
        return stOrgPhone;
    }

    public void setStOrgPhone(String stOrgPhone) {
        this.stOrgPhone = stOrgPhone;
    }

    @Basic
    @Column(name = "ST_ORG_POST")
    public String getStOrgPost() {
        return stOrgPost;
    }

    public void setStOrgPost(String stOrgPost) {
        this.stOrgPost = stOrgPost;
    }

    @Basic
    @Column(name = "ST_ORG_FOX")
    public String getStOrgFox() {
        return stOrgFox;
    }

    public void setStOrgFox(String stOrgFox) {
        this.stOrgFox = stOrgFox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TOrg org = (TOrg) o;

        if (stOrgId != null ? !stOrgId.equals(org.stOrgId) : org.stOrgId != null) return false;
        if (stOrgName != null ? !stOrgName.equals(org.stOrgName) : org.stOrgName != null) return false;
        if (stOrgCode != null ? !stOrgCode.equals(org.stOrgCode) : org.stOrgCode != null) return false;
        if (stOrgDomain != null ? !stOrgDomain.equals(org.stOrgDomain) : org.stOrgDomain != null) return false;
        if (stParentOrgId != null ? !stParentOrgId.equals(org.stParentOrgId) : org.stParentOrgId != null) return false;
        if (stOrgShortName != null ? !stOrgShortName.equals(org.stOrgShortName) : org.stOrgShortName != null)
            return false;
        if (stOrgArea != null ? !stOrgArea.equals(org.stOrgArea) : org.stOrgArea != null) return false;
        if (stOrgAddress != null ? !stOrgAddress.equals(org.stOrgAddress) : org.stOrgAddress != null) return false;
        if (stOrgPhone != null ? !stOrgPhone.equals(org.stOrgPhone) : org.stOrgPhone != null) return false;
        if (stOrgPost != null ? !stOrgPost.equals(org.stOrgPost) : org.stOrgPost != null) return false;
        if (stOrgFox != null ? !stOrgFox.equals(org.stOrgFox) : org.stOrgFox != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stOrgId != null ? stOrgId.hashCode() : 0;
        result = 31 * result + (stOrgName != null ? stOrgName.hashCode() : 0);
        result = 31 * result + (stOrgCode != null ? stOrgCode.hashCode() : 0);
        result = 31 * result + (stOrgDomain != null ? stOrgDomain.hashCode() : 0);
        result = 31 * result + (stParentOrgId != null ? stParentOrgId.hashCode() : 0);
        result = 31 * result + (stOrgShortName != null ? stOrgShortName.hashCode() : 0);
        result = 31 * result + (stOrgArea != null ? stOrgArea.hashCode() : 0);
        result = 31 * result + (stOrgAddress != null ? stOrgAddress.hashCode() : 0);
        result = 31 * result + (stOrgPhone != null ? stOrgPhone.hashCode() : 0);
        result = 31 * result + (stOrgPost != null ? stOrgPost.hashCode() : 0);
        result = 31 * result + (stOrgFox != null ? stOrgFox.hashCode() : 0);
        return result;
    }
}
