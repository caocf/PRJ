package com.huzhouport.illegal.model;

public class IllegalReason implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int reasonId;
	private String reasonName;
	private String refReasonID;

	public int getReasonId() {
		return reasonId;
	}

	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getRefReasonID() {
		return refReasonID;
	}

	public void setRefReasonID(String refReasonID) {
		this.refReasonID = refReasonID;
	}

}
