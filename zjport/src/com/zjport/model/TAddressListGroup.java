package com.zjport.model;

/**
 * Created by TWQ on 2016/8/22.
 */
public class TAddressListGroup implements java.io.Serializable{
    private Integer stAddressListGroupId;
    private String stGroupName;
    private Integer stOrgId;


    public Integer getStAddressListGroupId() {
        return stAddressListGroupId;
    }

    public void setStAddressListGroupId(Integer stAddressListGroupId) {
        this.stAddressListGroupId = stAddressListGroupId;
    }


    public String getStGroupName() {
        return stGroupName;
    }

    public void setStGroupName(String stGroupName) {
        this.stGroupName = stGroupName;
    }


    public Integer getStOrgId() {
        return stOrgId;
    }

    public void setStOrgId(Integer stOrgId) {
        this.stOrgId = stOrgId;
    }
}
