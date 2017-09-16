package com.zjport.model;

import java.sql.Timestamp;

/**
 * Created by Will on 2016/9/28 10:29.
 */
public class TCurisePhoto {
    private Integer stCruisePhotoId;
    private String stCuriseMiddleId;
    private String stPhotoDescribe;
    private String stCruiseAddress;
    private Timestamp dtCurise;
    private String stSource;

    public TCurisePhoto(){
        this.dtCurise = new Timestamp(System.currentTimeMillis());
    }

    public Integer getStCruisePhotoId() {
        return stCruisePhotoId;
    }

    public void setStCruisePhotoId(Integer stCruisePhotoId) {
        this.stCruisePhotoId = stCruisePhotoId;
    }

    public String getStCuriseMiddleId() {
        return stCuriseMiddleId;
    }

    public void setStCuriseMiddleId(String stCuriseMiddleId) {
        this.stCuriseMiddleId = stCuriseMiddleId;
    }

    public String getStPhotoDescribe() {
        return stPhotoDescribe;
    }

    public void setStPhotoDescribe(String stPhotoDescribe) {
        this.stPhotoDescribe = stPhotoDescribe;
    }

    public String getStCruiseAddress() {
        return stCruiseAddress;
    }

    public void setStCruiseAddress(String stCruiseAddress) {
        this.stCruiseAddress = stCruiseAddress;
    }

    public Timestamp getDtCurise() {
        return dtCurise;
    }

    public void setDtCurise(Timestamp dtCurise) {
        this.dtCurise = dtCurise;
    }

    public String getStSource() {
        return stSource;
    }

    public void setStSource(String stSource) {
        this.stSource = stSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCurisePhoto that = (TCurisePhoto) o;

        if (stCruisePhotoId != null ? !stCruisePhotoId.equals(that.stCruisePhotoId) : that.stCruisePhotoId != null)
            return false;
        if (stCuriseMiddleId != null ? !stCuriseMiddleId.equals(that.stCuriseMiddleId) : that.stCuriseMiddleId != null)
            return false;
        if (stPhotoDescribe != null ? !stPhotoDescribe.equals(that.stPhotoDescribe) : that.stPhotoDescribe != null)
            return false;
        if (stCruiseAddress != null ? !stCruiseAddress.equals(that.stCruiseAddress) : that.stCruiseAddress != null)
            return false;
        if (dtCurise != null ? !dtCurise.equals(that.dtCurise) : that.dtCurise != null) return false;
        if (stSource != null ? !stSource.equals(that.stSource) : that.stSource != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stCruisePhotoId != null ? stCruisePhotoId.hashCode() : 0;
        result = 31 * result + (stCuriseMiddleId != null ? stCuriseMiddleId.hashCode() : 0);
        result = 31 * result + (stPhotoDescribe != null ? stPhotoDescribe.hashCode() : 0);
        result = 31 * result + (stCruiseAddress != null ? stCruiseAddress.hashCode() : 0);
        result = 31 * result + (dtCurise != null ? dtCurise.hashCode() : 0);
        result = 31 * result + (stSource != null ? stSource.hashCode() : 0);
        return result;
    }
}
