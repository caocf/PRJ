package com.zjport.model;

import java.sql.Timestamp;

/**
 * Created by Will on 2016/9/28 10:29.
 */
public class TCurise {
    /**
     * 有效
     */
    public static final int VALID = 1;
    /**
     * 无效
     */
    public static final int INVAILD = 0;

    private Integer stCruiseId;
    private String stChannel;
    private String stCruiseFrom;
    private String stCruiseTo;
    private Timestamp dtCuriseBegin;
    private Timestamp dtCuriseEnd;
    private Integer stCruiseUser;
    private String stCuriseMiddleId;
    private Integer stIsValid;
    private String stCuriseContent;

    public TCurise(){
        this.stIsValid = VALID;
        this.dtCuriseBegin = new Timestamp(System.currentTimeMillis());
    }

    public Integer getStCruiseId() {
        return stCruiseId;
    }

    public void setStCruiseId(Integer stCruiseId) {
        this.stCruiseId = stCruiseId;
    }

    public String getStChannel() {
        return stChannel;
    }

    public void setStChannel(String stChannel) {
        this.stChannel = stChannel;
    }

    public String getStCruiseFrom() {
        return stCruiseFrom;
    }

    public void setStCruiseFrom(String stCruiseFrom) {
        this.stCruiseFrom = stCruiseFrom;
    }

    public String getStCruiseTo() {
        return stCruiseTo;
    }

    public void setStCruiseTo(String stCruiseTo) {
        this.stCruiseTo = stCruiseTo;
    }

    public Timestamp getDtCuriseBegin() {
        return dtCuriseBegin;
    }

    public void setDtCuriseBegin(Timestamp dtCuriseBegin) {
        this.dtCuriseBegin = dtCuriseBegin;
    }

    public Timestamp getDtCuriseEnd() {
        return dtCuriseEnd;
    }

    public void setDtCuriseEnd(Timestamp dtCuriseEnd) {
        this.dtCuriseEnd = dtCuriseEnd;
    }

    public Integer getStCruiseUser() {
        return stCruiseUser;
    }

    public void setStCruiseUser(Integer stCruiseUser) {
        this.stCruiseUser = stCruiseUser;
    }

    public String getStCuriseMiddleId() {
        return stCuriseMiddleId;
    }

    public void setStCuriseMiddleId(String stCuriseMiddleId) {
        this.stCuriseMiddleId = stCuriseMiddleId;
    }

    public Integer getStIsValid() {
        return stIsValid;
    }

    public void setStIsValid(Integer stIsValid) {
        this.stIsValid = stIsValid;
    }

    public String getStCuriseContent() {
        return stCuriseContent;
    }

    public void setStCuriseContent(String stCuriseContent) {
        this.stCuriseContent = stCuriseContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCurise tCurise = (TCurise) o;

        if (stCruiseId != null ? !stCruiseId.equals(tCurise.stCruiseId) : tCurise.stCruiseId != null) return false;
        if (stChannel != null ? !stChannel.equals(tCurise.stChannel) : tCurise.stChannel != null) return false;
        if (stCruiseFrom != null ? !stCruiseFrom.equals(tCurise.stCruiseFrom) : tCurise.stCruiseFrom != null)
            return false;
        if (stCruiseTo != null ? !stCruiseTo.equals(tCurise.stCruiseTo) : tCurise.stCruiseTo != null) return false;
        if (dtCuriseBegin != null ? !dtCuriseBegin.equals(tCurise.dtCuriseBegin) : tCurise.dtCuriseBegin != null)
            return false;
        if (dtCuriseEnd != null ? !dtCuriseEnd.equals(tCurise.dtCuriseEnd) : tCurise.dtCuriseEnd != null) return false;
        if (stCruiseUser != null ? !stCruiseUser.equals(tCurise.stCruiseUser) : tCurise.stCruiseUser != null)
            return false;
        if (stCuriseMiddleId != null ? !stCuriseMiddleId.equals(tCurise.stCuriseMiddleId) : tCurise.stCuriseMiddleId != null)
            return false;
        if (stIsValid != null ? !stIsValid.equals(tCurise.stIsValid) : tCurise.stIsValid != null) return false;
        if (stCuriseContent != null ? !stCuriseContent.equals(tCurise.stCuriseContent) : tCurise.stCuriseContent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stCruiseId != null ? stCruiseId.hashCode() : 0;
        result = 31 * result + (stChannel != null ? stChannel.hashCode() : 0);
        result = 31 * result + (stCruiseFrom != null ? stCruiseFrom.hashCode() : 0);
        result = 31 * result + (stCruiseTo != null ? stCruiseTo.hashCode() : 0);
        result = 31 * result + (dtCuriseBegin != null ? dtCuriseBegin.hashCode() : 0);
        result = 31 * result + (dtCuriseEnd != null ? dtCuriseEnd.hashCode() : 0);
        result = 31 * result + (stCruiseUser != null ? stCruiseUser.hashCode() : 0);
        result = 31 * result + (stCuriseMiddleId != null ? stCuriseMiddleId.hashCode() : 0);
        result = 31 * result + (stIsValid != null ? stIsValid.hashCode() : 0);
        result = 31 * result + (stCuriseContent != null ? stCuriseContent.hashCode() : 0);
        return result;
    }
}
