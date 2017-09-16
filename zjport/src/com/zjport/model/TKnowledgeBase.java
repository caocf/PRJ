package com.zjport.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by TWQ on 2016/9/6.
 */
@Entity
@Table(name = "t_knowledge_base", schema = "zjport", catalog = "")
public class TKnowledgeBase {
    private int stBaseId;
    private String stBaseTitle;
    private Timestamp dtSend;
    private Integer stStructureId;
    private String stAttachmentMiddleId;
    private String stBaseContent;
    private Integer stUserId;
    private String stBaseKeyword;
    private Boolean stIsValid;

    @Id
    @Column(name = "ST_BASE_ID")
    public int getStBaseId() {
        return stBaseId;
    }

    public void setStBaseId(int stBaseId) {
        this.stBaseId = stBaseId;
    }

    @Basic
    @Column(name = "ST_BASE_TITLE")
    public String getStBaseTitle() {
        return stBaseTitle;
    }

    public void setStBaseTitle(String stBaseTitle) {
        this.stBaseTitle = stBaseTitle;
    }

    @Basic
    @Column(name = "DT_SEND")
    public Timestamp getDtSend() {
        return dtSend;
    }

    public void setDtSend(Timestamp dtSend) {
        this.dtSend = dtSend;
    }

    @Basic
    @Column(name = "ST_STRUCTURE_ID")
    public Integer getStStructureId() {
        return stStructureId;
    }

    public void setStStructureId(Integer stStructureId) {
        this.stStructureId = stStructureId;
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
    @Column(name = "ST_BASE_CONTENT")
    public String getStBaseContent() {
        return stBaseContent;
    }

    public void setStBaseContent(String stBaseContent) {
        this.stBaseContent = stBaseContent;
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
    @Column(name = "ST_BASE_KEYWORD")
    public String getStBaseKeyword() {
        return stBaseKeyword;
    }

    public void setStBaseKeyword(String stBaseKeyword) {
        this.stBaseKeyword = stBaseKeyword;
    }

    @Basic
    @Column(name = "ST_IS_VALID")
    public Boolean getStIsValid() {
        return stIsValid;
    }

    public void setStIsValid(Boolean stIsValid) {
        this.stIsValid = stIsValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TKnowledgeBase that = (TKnowledgeBase) o;

        if (stBaseId != that.stBaseId) return false;
        if (stBaseTitle != null ? !stBaseTitle.equals(that.stBaseTitle) : that.stBaseTitle != null) return false;
        if (dtSend != null ? !dtSend.equals(that.dtSend) : that.dtSend != null) return false;
        if (stStructureId != null ? !stStructureId.equals(that.stStructureId) : that.stStructureId != null)
            return false;
        if (stAttachmentMiddleId != null ? !stAttachmentMiddleId.equals(that.stAttachmentMiddleId) : that.stAttachmentMiddleId != null)
            return false;
        if (stBaseContent != null ? !stBaseContent.equals(that.stBaseContent) : that.stBaseContent != null)
            return false;
        if (stUserId != null ? !stUserId.equals(that.stUserId) : that.stUserId != null) return false;
        if (stBaseKeyword != null ? !stBaseKeyword.equals(that.stBaseKeyword) : that.stBaseKeyword != null)
            return false;
        if (stIsValid != null ? !stIsValid.equals(that.stIsValid) : that.stIsValid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stBaseId;
        result = 31 * result + (stBaseTitle != null ? stBaseTitle.hashCode() : 0);
        result = 31 * result + (dtSend != null ? dtSend.hashCode() : 0);
        result = 31 * result + (stStructureId != null ? stStructureId.hashCode() : 0);
        result = 31 * result + (stAttachmentMiddleId != null ? stAttachmentMiddleId.hashCode() : 0);
        result = 31 * result + (stBaseContent != null ? stBaseContent.hashCode() : 0);
        result = 31 * result + (stUserId != null ? stUserId.hashCode() : 0);
        result = 31 * result + (stBaseKeyword != null ? stBaseKeyword.hashCode() : 0);
        result = 31 * result + (stIsValid != null ? stIsValid.hashCode() : 0);
        return result;
    }
}
