package com.zjport.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Entity
@Table(name = "t_org_sync", schema = "zjport", catalog = "")
public class TOrgSync {
    private String stOrgSyncId;
    private String stOrgName;
    private String stOrgCode;
    private String stOrgDm;
    private String stOrgType;
    private String stOrgDomain;
    private String stParentOrg;
    private Integer stAddress;
    private Boolean stIfScan;
    private Boolean stIfNode;
    private String stOrgShortName;
    private String stOrgArea;

    @Id
    @Column(name = "ST_ORG_SYNC_ID")
    public String getStOrgSyncId() {
        return stOrgSyncId;
    }

    public void setStOrgSyncId(String stOrgSyncId) {
        this.stOrgSyncId = stOrgSyncId;
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
    @Column(name = "ST_ORG_DM")
    public String getStOrgDm() {
        return stOrgDm;
    }

    public void setStOrgDm(String stOrgDm) {
        this.stOrgDm = stOrgDm;
    }

    @Basic
    @Column(name = "ST_ORG_TYPE")
    public String getStOrgType() {
        return stOrgType;
    }

    public void setStOrgType(String stOrgType) {
        this.stOrgType = stOrgType;
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
    @Column(name = "ST_PARENT_ORG")
    public String getStParentOrg() {
        return stParentOrg;
    }

    public void setStParentOrg(String stParentOrg) {
        this.stParentOrg = stParentOrg;
    }

    @Basic
    @Column(name = "ST_ADDRESS")
    public Integer getStAddress() {
        return stAddress;
    }

    public void setStAddress(Integer stAddress) {
        this.stAddress = stAddress;
    }

    @Basic
    @Column(name = "ST_IF_SCAN")
    public Boolean getStIfScan() {
        return stIfScan;
    }

    public void setStIfScan(Boolean stIfScan) {
        this.stIfScan = stIfScan;
    }

    @Basic
    @Column(name = "ST_IF_NODE")
    public Boolean getStIfNode() {
        return stIfNode;
    }

    public void setStIfNode(Boolean stIfNode) {
        this.stIfNode = stIfNode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TOrgSync tOrgSync = (TOrgSync) o;

        if (stOrgSyncId != null ? !stOrgSyncId.equals(tOrgSync.stOrgSyncId) : tOrgSync.stOrgSyncId != null)
            return false;
        if (stOrgName != null ? !stOrgName.equals(tOrgSync.stOrgName) : tOrgSync.stOrgName != null) return false;
        if (stOrgCode != null ? !stOrgCode.equals(tOrgSync.stOrgCode) : tOrgSync.stOrgCode != null) return false;
        if (stOrgDm != null ? !stOrgDm.equals(tOrgSync.stOrgDm) : tOrgSync.stOrgDm != null) return false;
        if (stOrgType != null ? !stOrgType.equals(tOrgSync.stOrgType) : tOrgSync.stOrgType != null) return false;
        if (stOrgDomain != null ? !stOrgDomain.equals(tOrgSync.stOrgDomain) : tOrgSync.stOrgDomain != null)
            return false;
        if (stParentOrg != null ? !stParentOrg.equals(tOrgSync.stParentOrg) : tOrgSync.stParentOrg != null)
            return false;
        if (stAddress != null ? !stAddress.equals(tOrgSync.stAddress) : tOrgSync.stAddress != null) return false;
        if (stIfScan != null ? !stIfScan.equals(tOrgSync.stIfScan) : tOrgSync.stIfScan != null) return false;
        if (stIfNode != null ? !stIfNode.equals(tOrgSync.stIfNode) : tOrgSync.stIfNode != null) return false;
        if (stOrgShortName != null ? !stOrgShortName.equals(tOrgSync.stOrgShortName) : tOrgSync.stOrgShortName != null)
            return false;
        if (stOrgArea != null ? !stOrgArea.equals(tOrgSync.stOrgArea) : tOrgSync.stOrgArea != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stOrgSyncId != null ? stOrgSyncId.hashCode() : 0;
        result = 31 * result + (stOrgName != null ? stOrgName.hashCode() : 0);
        result = 31 * result + (stOrgCode != null ? stOrgCode.hashCode() : 0);
        result = 31 * result + (stOrgDm != null ? stOrgDm.hashCode() : 0);
        result = 31 * result + (stOrgType != null ? stOrgType.hashCode() : 0);
        result = 31 * result + (stOrgDomain != null ? stOrgDomain.hashCode() : 0);
        result = 31 * result + (stParentOrg != null ? stParentOrg.hashCode() : 0);
        result = 31 * result + (stAddress != null ? stAddress.hashCode() : 0);
        result = 31 * result + (stIfScan != null ? stIfScan.hashCode() : 0);
        result = 31 * result + (stIfNode != null ? stIfNode.hashCode() : 0);
        result = 31 * result + (stOrgShortName != null ? stOrgShortName.hashCode() : 0);
        result = 31 * result + (stOrgArea != null ? stOrgArea.hashCode() : 0);
        return result;
    }
}
