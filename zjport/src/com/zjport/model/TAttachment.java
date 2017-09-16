package com.zjport.model;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by TWQ on 2016/8/23.
 */
@Entity
@Table(name = "t_attachment", schema = "zjport", catalog = "")
public class TAttachment {
    private int stAttachmentId;
    private String stAttachmentMiddleId;
    private String stFileName;
    private String stFileType;
    private Integer stFileSize;
    private byte[] stFileContent;
    private String stFileDescribe;
    private String stFileSource;

    @Id
    @Column(name = "ST_ATTACHMENT_ID")
    public int getStAttachmentId() {
        return stAttachmentId;
    }

    public void setStAttachmentId(int stAttachmentId) {
        this.stAttachmentId = stAttachmentId;
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
    @Column(name = "ST_FILE_NAME")
    public String getStFileName() {
        return stFileName;
    }

    public void setStFileName(String stFileName) {
        this.stFileName = stFileName;
    }

    @Basic
    @Column(name = "ST_FILE_TYPE")
    public String getStFileType() {
        return stFileType;
    }

    public void setStFileType(String stFileType) {
        this.stFileType = stFileType;
    }

    @Basic
    @Column(name = "ST_FILE_SIZE")
    public Integer getStFileSize() {
        return stFileSize;
    }

    public void setStFileSize(Integer stFileSize) {
        this.stFileSize = stFileSize;
    }

    @Basic
    @Column(name = "ST_FILE_CONTENT")
    public byte[] getStFileContent() {
        return stFileContent;
    }

    public void setStFileContent(byte[] stFileContent) {
        this.stFileContent = stFileContent;
    }

    @Basic
    @Column(name = "ST_FILE_DESCRIBE")
    public String getStFileDescribe() {
        return stFileDescribe;
    }

    public void setStFileDescribe(String stFileDescribe) {
        this.stFileDescribe = stFileDescribe;
    }

    @Basic
    @Column(name = "ST_FILE_SOURCE")
    public String getStFileSource() {
        return stFileSource;
    }

    public void setStFileSource(String stFileSource) {
        this.stFileSource = stFileSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TAttachment that = (TAttachment) o;

        if (stAttachmentId != that.stAttachmentId) return false;
        if (stAttachmentMiddleId != null ? !stAttachmentMiddleId.equals(that.stAttachmentMiddleId) : that.stAttachmentMiddleId != null)
            return false;
        if (stFileName != null ? !stFileName.equals(that.stFileName) : that.stFileName != null) return false;
        if (stFileType != null ? !stFileType.equals(that.stFileType) : that.stFileType != null) return false;
        if (stFileSize != null ? !stFileSize.equals(that.stFileSize) : that.stFileSize != null) return false;
        if (!Arrays.equals(stFileContent, that.stFileContent)) return false;
        if (stFileDescribe != null ? !stFileDescribe.equals(that.stFileDescribe) : that.stFileDescribe != null)
            return false;
        if (stFileSource != null ? !stFileSource.equals(that.stFileSource) : that.stFileSource != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stAttachmentId;
        result = 31 * result + (stAttachmentMiddleId != null ? stAttachmentMiddleId.hashCode() : 0);
        result = 31 * result + (stFileName != null ? stFileName.hashCode() : 0);
        result = 31 * result + (stFileType != null ? stFileType.hashCode() : 0);
        result = 31 * result + (stFileSize != null ? stFileSize.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(stFileContent);
        result = 31 * result + (stFileDescribe != null ? stFileDescribe.hashCode() : 0);
        result = 31 * result + (stFileSource != null ? stFileSource.hashCode() : 0);
        return result;
    }
}
