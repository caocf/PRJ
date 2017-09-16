package com.huzhouport.inspection.model;

public class InspectionSupplement implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
	private int inspectionId;
	private int userId;
	private String supplementTime;//补充时间
	
	public int getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(int inspectionId) {
		this.inspectionId = inspectionId;
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
