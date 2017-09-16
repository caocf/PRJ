package com.zjport.model;

import java.sql.Timestamp;

/**
 * Created by TWQ on 2016/8/23.
 */
public class TInformation implements java.io.Serializable{

    private int stInformId;
    private String stInformTitle;
    private String stInformContent;
    private String stInformKeyword;
    private Integer stWebNameId;
    private Integer stWebColumnId;
    private String stInformColor;
    private Integer stInformFromId;
    private Timestamp dtCreate;
    private Timestamp dtSend;
    private String stType;
    private String stState;
    private String stAttachmentMiddleId;
    private Integer stApprovalUserId;
    private String stBackContent;
    private Timestamp dtApproval;
    private Boolean stIsValid;
    private String stScanDepartMiddleId;
    private String stInformObject;
    private String stResult;
    private String stTemplet;
    private String stFailnum;

    public int getStInformId() {
        return stInformId;
    }

    public void setStInformId(int stInformId) {
        this.stInformId = stInformId;
    }

    public String getStInformTitle() {
        return stInformTitle;
    }

    public void setStInformTitle(String stInformTitle) {
        this.stInformTitle = stInformTitle;
    }

    public String getStInformContent() {
        return stInformContent;
    }

    public void setStInformContent(String stInformContent) {
        this.stInformContent = stInformContent;
    }

    public String getStInformKeyword() {
        return stInformKeyword;
    }

    public void setStInformKeyword(String stInformKeyword) {
        this.stInformKeyword = stInformKeyword;
    }

    public Integer getStWebNameId() {
        return stWebNameId;
    }

    public void setStWebNameId(Integer stWebNameId) {
        this.stWebNameId = stWebNameId;
    }

    public Integer getStWebColumnId() {
        return stWebColumnId;
    }

    public void setStWebColumnId(Integer stWebColumnId) {
        this.stWebColumnId = stWebColumnId;
    }

    public String getStInformColor() {
        return stInformColor;
    }

    public void setStInformColor(String stInformColor) {
        this.stInformColor = stInformColor;
    }

    public Integer getStInformFromId() {
        return stInformFromId;
    }

    public void setStInformFromId(Integer stInformFromId) {
        this.stInformFromId = stInformFromId;
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Timestamp getDtSend() {
        return dtSend;
    }

    public void setDtSend(Timestamp dtSend) {
        this.dtSend = dtSend;
    }

    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType;
    }

    public String getStState() {
        return stState;
    }

    public void setStState(String stState) {
        this.stState = stState;
    }

    public String getStAttachmentMiddleId() {
        return stAttachmentMiddleId;
    }

    public void setStAttachmentMiddleId(String stAttachmentMiddleId) {
        this.stAttachmentMiddleId = stAttachmentMiddleId;
    }

    public Integer getStApprovalUserId() {
        return stApprovalUserId;
    }

    public void setStApprovalUserId(Integer stApprovalUserId) {
        this.stApprovalUserId = stApprovalUserId;
    }

    public String getStBackContent() {
        return stBackContent;
    }

    public void setStBackContent(String stBackContent) {
        this.stBackContent = stBackContent;
    }

    public Timestamp getDtApproval() {
        return dtApproval;
    }

    public void setDtApproval(Timestamp dtApproval) {
        this.dtApproval = dtApproval;
    }

    public Boolean getStIsValid() {
        return stIsValid;
    }

    public void setStIsValid(Boolean stIsValid) {
        this.stIsValid = stIsValid;
    }

    public String getStScanDepartMiddleId() {
        return stScanDepartMiddleId;
    }

    public void setStScanDepartMiddleId(String stScanDepartMiddleId) {
        this.stScanDepartMiddleId = stScanDepartMiddleId;
    }

    public String getStInformObject() {
        return stInformObject;
    }

    public void setStInformObject(String stInformObject) {
        this.stInformObject = stInformObject;
    }

    public String getStResult() {
        return stResult;
    }

    public void setStResult(String stResult) {
        this.stResult = stResult;
    }

    public String getStTemplet() {
        return stTemplet;
    }

    public void setStTemplet(String stTemplet) {
        this.stTemplet = stTemplet;
    }

    public String getStFailnum() {
        return stFailnum;
    }

    public void setStFailnum(String stFailnum) {
        this.stFailnum = stFailnum;
    }
}
