package com.zjport.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by TWQ on 2016/9/14.
 */
@Entity
@Table(name = "t_calendar", schema = "zjport", catalog = "")
public class TCalendar {
    private int stCalendarId;
    private String stCalendarTitle;
    private Integer stUserId;
    private Timestamp dtStart;
    private Timestamp dtEnd;
    private String stContent;
    private String stType;
    private String stCalendarMiddleId;
    private String stUrgentState;
    private String stAttachmentMiddleId;
    private Boolean stIsAlert;

    @Id
    @Column(name = "ST_CALENDAR_ID")
    public int getStCalendarId() {
        return stCalendarId;
    }

    public void setStCalendarId(int stCalendarId) {
        this.stCalendarId = stCalendarId;
    }

    @Basic
    @Column(name = "ST_CALENDAR_TITLE")
    public String getStCalendarTitle() {
        return stCalendarTitle;
    }

    public void setStCalendarTitle(String stCalendarTitle) {
        this.stCalendarTitle = stCalendarTitle;
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
    @Column(name = "DT_START")
    public Timestamp getDtStart() {
        return dtStart;
    }

    public void setDtStart(Timestamp dtStart) {
        this.dtStart = dtStart;
    }

    @Basic
    @Column(name = "DT_END")
    public Timestamp getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(Timestamp dtEnd) {
        this.dtEnd = dtEnd;
    }

    @Basic
    @Column(name = "ST_CONTENT")
    public String getStContent() {
        return stContent;
    }

    public void setStContent(String stContent) {
        this.stContent = stContent;
    }

    @Basic
    @Column(name = "ST_TYPE")
    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType;
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
    @Column(name = "ST_URGENT_STATE")
    public String getStUrgentState() {
        return stUrgentState;
    }

    public void setStUrgentState(String stUrgentState) {
        this.stUrgentState = stUrgentState;
    }

    @Basic
    @Column(name = "ST_ATTACHMENT_MIDDLE_ID")
    public String getStAttachmentMiddleId() {
        return stAttachmentMiddleId;
    }

    public void setStAttachmentMiddleId(String stAttachmentMiddleId) {
        this.stAttachmentMiddleId = stAttachmentMiddleId;
    }

    @Basic
    @Column(name = "ST_IS_ALERT")
    public Boolean getStIsAlert() {
        return stIsAlert;
    }

    public void setStIsAlert(Boolean stIsAlert) {
        this.stIsAlert = stIsAlert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCalendar calendar = (TCalendar) o;

        if (stCalendarId != calendar.stCalendarId) return false;
        if (stCalendarTitle != null ? !stCalendarTitle.equals(calendar.stCalendarTitle) : calendar.stCalendarTitle != null)
            return false;
        if (stUserId != null ? !stUserId.equals(calendar.stUserId) : calendar.stUserId != null) return false;
        if (dtStart != null ? !dtStart.equals(calendar.dtStart) : calendar.dtStart != null) return false;
        if (dtEnd != null ? !dtEnd.equals(calendar.dtEnd) : calendar.dtEnd != null) return false;
        if (stContent != null ? !stContent.equals(calendar.stContent) : calendar.stContent != null) return false;
        if (stType != null ? !stType.equals(calendar.stType) : calendar.stType != null) return false;
        if (stCalendarMiddleId != null ? !stCalendarMiddleId.equals(calendar.stCalendarMiddleId) : calendar.stCalendarMiddleId != null)
            return false;
        if (stUrgentState != null ? !stUrgentState.equals(calendar.stUrgentState) : calendar.stUrgentState != null)
            return false;
        if (stAttachmentMiddleId != null ? !stAttachmentMiddleId.equals(calendar.stAttachmentMiddleId) : calendar.stAttachmentMiddleId != null)
            return false;
        if (stIsAlert != null ? !stIsAlert.equals(calendar.stIsAlert) : calendar.stIsAlert != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stCalendarId;
        result = 31 * result + (stCalendarTitle != null ? stCalendarTitle.hashCode() : 0);
        result = 31 * result + (stUserId != null ? stUserId.hashCode() : 0);
        result = 31 * result + (dtStart != null ? dtStart.hashCode() : 0);
        result = 31 * result + (dtEnd != null ? dtEnd.hashCode() : 0);
        result = 31 * result + (stContent != null ? stContent.hashCode() : 0);
        result = 31 * result + (stType != null ? stType.hashCode() : 0);
        result = 31 * result + (stCalendarMiddleId != null ? stCalendarMiddleId.hashCode() : 0);
        result = 31 * result + (stUrgentState != null ? stUrgentState.hashCode() : 0);
        result = 31 * result + (stAttachmentMiddleId != null ? stAttachmentMiddleId.hashCode() : 0);
        result = 31 * result + (stIsAlert != null ? stIsAlert.hashCode() : 0);
        return result;
    }
}
