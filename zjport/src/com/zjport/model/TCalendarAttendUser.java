package com.zjport.model;

import javax.persistence.*;

/**
 * Created by TWQ on 2016/9/14.
 */
@Entity
@Table(name = "t_calendar_attend_user", schema = "zjport", catalog = "")
public class TCalendarAttendUser {
    private int stCalendarAttendUserId;
    private String stCalendarMiddleId;
    private Integer stUserId;

    @Id
    @Column(name = "ST_CALENDAR_ATTEND_USER_ID")
    public int getStCalendarAttendUserId() {
        return stCalendarAttendUserId;
    }

    public void setStCalendarAttendUserId(int stCalendarAttendUserId) {
        this.stCalendarAttendUserId = stCalendarAttendUserId;
    }

    @Basic
    @Column(name = "ST_CALENDAR_MIDDLE_ID")
    public String getStCalendarMiddleId() {
        return stCalendarMiddleId;
    }

    public void setStCalendarMiddleId(String stCalendarMiddleId) {
        this.stCalendarMiddleId = stCalendarMiddleId;
    }

    @Basic
    @Column(name = "ST_USER_ID")
    public Integer getStUserId() {
        return stUserId;
    }

    public void setStUserId(Integer stUserId) {
        this.stUserId = stUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCalendarAttendUser that = (TCalendarAttendUser) o;

        if (stCalendarAttendUserId != that.stCalendarAttendUserId) return false;
        if (stCalendarMiddleId != null ? !stCalendarMiddleId.equals(that.stCalendarMiddleId) : that.stCalendarMiddleId != null)
            return false;
        if (stUserId != null ? !stUserId.equals(that.stUserId) : that.stUserId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stCalendarAttendUserId;
        result = 31 * result + (stCalendarMiddleId != null ? stCalendarMiddleId.hashCode() : 0);
        result = 31 * result + (stUserId != null ? stUserId.hashCode() : 0);
        return result;
    }
}
