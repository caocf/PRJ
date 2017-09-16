package com.zjport.model;

import javax.persistence.*;

/**
 * Created by TWQ on 2016/9/28.
 */
@Entity
@Table(name = "t_address_list_middle", schema = "zjport", catalog = "")
public class TAddressListMiddle {
    private int stAddressListMiddleId;
    private Integer stUserId;
    private Integer stAddressListGroupId;

    @Id
    @Column(name = "ST_ADDRESS_LIST_MIDDLE_ID")
    public int getStAddressListMiddleId() {
        return stAddressListMiddleId;
    }

    public void setStAddressListMiddleId(int stAddressListMiddleId) {
        this.stAddressListMiddleId = stAddressListMiddleId;
    }

    @Basic
    @Column(name = "ST_USER_ID")
    public Integer getStUserId() {
        return stUserId;
    }

    public void setStUserId(Integer stUserId) {
        this.stUserId = stUserId;
    }

    @Basic
    @Column(name = "ST_ADDRESS_LIST_GROUP_ID")
    public Integer getStAddressListGroupId() {
        return stAddressListGroupId;
    }

    public void setStAddressListGroupId(Integer stAddressListGroupId) {
        this.stAddressListGroupId = stAddressListGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TAddressListMiddle that = (TAddressListMiddle) o;

        if (stAddressListMiddleId != that.stAddressListMiddleId) return false;
        if (stUserId != null ? !stUserId.equals(that.stUserId) : that.stUserId != null) return false;
        if (stAddressListGroupId != null ? !stAddressListGroupId.equals(that.stAddressListGroupId) : that.stAddressListGroupId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stAddressListMiddleId;
        result = 31 * result + (stUserId != null ? stUserId.hashCode() : 0);
        result = 31 * result + (stAddressListGroupId != null ? stAddressListGroupId.hashCode() : 0);
        return result;
    }
}
