package com.huzhouport.schedule.model;

import java.io.File;
import java.util.List;

public class ScheduleAttachment implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int attachmentId;
	private String attachmentName;
	private String attachmentPath;
	private List<File> af;
	private List<String> afFileName;
	private List<String> afContentType;
	public int getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public List<File> getAf() {
		return af;
	}
	public void setAf(List<File> af) {
		this.af = af;
	}
	public List<String> getAfFileName() {
		return afFileName;
	}
	public void setAfFileName(List<String> afFileName) {
		this.afFileName = afFileName;
	}
	public List<String> getAfContentType() {
		return afContentType;
	}
	public void setAfContentType(List<String> afContentType) {
		this.afContentType = afContentType;
	}
	
	
	
}
