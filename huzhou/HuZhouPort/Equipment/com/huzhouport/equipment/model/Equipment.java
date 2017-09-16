package com.huzhouport.equipment.model;

public class Equipment implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int equipmentID;  //id
	private int equipmentKind;  //申请的物品
	private int equipmentUser;  //申请人
	private String equipmentReason;  //申请原因
	private String equipmentDate;  //申请时间
	private int approvalID;  // 审批人id
	private int approvalResult; //审批结果
	private String approvalOpinion; //审批原因
	private String approvalTime; //审批时间
	
	public int getEquipmentID() {
		return equipmentID;
	}
	public void setEquipmentID(int equipmentID) {
		this.equipmentID = equipmentID;
	}
	public int getEquipmentKind() {
		return equipmentKind;
	}
	public void setEquipmentKind(int equipmentKind) {
		this.equipmentKind = equipmentKind;
	}
	public int getEquipmentUser() {
		return equipmentUser;
	}
	public void setEquipmentUser(int equipmentUser) {
		this.equipmentUser = equipmentUser;
	}
	public String getEquipmentReason() {
		return equipmentReason;
	}
	public void setEquipmentReason(String equipmentReason) {
		this.equipmentReason = equipmentReason;
	}
	public String getEquipmentDate() {
		return equipmentDate;
	}
	public void setEquipmentDate(String equipmentDate) {
		this.equipmentDate = equipmentDate;
	}
	public int getApprovalID() {
		return approvalID;
	}
	public void setApprovalID(int approvalID) {
		this.approvalID = approvalID;
	}
	public int getApprovalResult() {
		return approvalResult;
	}
	public void setApprovalResult(int approvalResult) {
		this.approvalResult = approvalResult;
	}
	public String getApprovalOpinion() {
		return approvalOpinion;
	}
	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
	public String getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	
	
}
