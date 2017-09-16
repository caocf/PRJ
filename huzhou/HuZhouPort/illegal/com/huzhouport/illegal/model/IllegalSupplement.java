package com.huzhouport.illegal.model;

public class IllegalSupplement implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
	private int illegalId;
	private int userId;
	private String supplementTime;//补充时间
	public int getIllegalId() {
		return illegalId;
	}
	public void setIllegalId(int illegalId) {
		this.illegalId = illegalId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSupplementTime() {
		return supplementTime;
	}
	public void setSupplementTime(String supplementTime) {
		this.supplementTime = supplementTime;
	}
	
	

}
