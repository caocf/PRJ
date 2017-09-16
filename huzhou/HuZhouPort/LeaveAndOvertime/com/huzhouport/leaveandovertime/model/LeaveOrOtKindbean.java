package com.huzhouport.leaveandovertime.model;

public class LeaveOrOtKindbean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int kindID;  //类型编号
	private String kindName;  //类别名称
	private int kindType;  //请假OR加班
	private int DefauleDate;  //默认天数
	
	private int leaveOrOtID;  //记录编号
	private int leaveOrOtKind; //请假加班类型
	private String leaveOrOtUser;  //申请人
	private String leaveOrOtReason; //申请原因
	private String leaveOrOtDate; //申请日期
	private String beginDate;  //实际开始日期
	private int lastDate; //申请天数
	private String endDate;  //实际结束日期
	private String approvalID1;  //直接领导名字
	private int approvalResult1; //直接领导审批结果
	private String approvalOpinion1;  //直接领导审批意见
	private String approvalTime1;  //审批时间
	private String approvalID2;   //上级领导名字
	private int approvalResult2; //上级领导审批结果
	private String approvalOpinion2; //上级领导审批意见
	private String approvalTime2;
	private String approvalID3;  //分管领导名字
	private int approvalResult3; //分管领导审批结果
	private String approvalOpinion3; //分管领导审批意见
	private String approvalTime3;
	private String approvalResult; // 审批结果
	
	private String approvalName; //审批人
	private int approvalID; //审批人的id
	
	private String address;//出差地点
	public int getKindID() {
		return kindID;
	}

	public void setKindID(int kindID) {
		this.kindID = kindID;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public int getKindType() {
		return kindType;
	}

	public void setKindType(int kindType) {
		this.kindType = kindType;
	}

	public int getDefauleDate() {
		return DefauleDate;
	}

	public void setDefauleDate(int defauleDate) {
		DefauleDate = defauleDate;
	}

	public int getLeaveOrOtID() {
		return leaveOrOtID;
	}

	public void setLeaveOrOtID(int leaveOrOtID) {
		this.leaveOrOtID = leaveOrOtID;
	}

	public int getLeaveOrOtKind() {
		return leaveOrOtKind;
	}

	public void setLeaveOrOtKind(int leaveOrOtKind) {
		this.leaveOrOtKind = leaveOrOtKind;
	}


	public String getLeaveOrOtUser() {
		return leaveOrOtUser;
	}

	public void setLeaveOrOtUser(String leaveOrOtUser) {
		this.leaveOrOtUser = leaveOrOtUser;
	}

	public String getLeaveOrOtReason() {
		return leaveOrOtReason;
	}

	public void setLeaveOrOtReason(String leaveOrOtReason) {
		this.leaveOrOtReason = leaveOrOtReason;
	}

	public String getLeaveOrOtDate() {
		return leaveOrOtDate;
	}

	public void setLeaveOrOtDate(String leaveOrOtDate) {
		this.leaveOrOtDate = leaveOrOtDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public int getLastDate() {
		return lastDate;
	}

	public void setLastDate(int lastDate) {
		this.lastDate = lastDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getApprovalID1() {
		return approvalID1;
	}

	public void setApprovalID1(String approvalID1) {
		this.approvalID1 = approvalID1;
	}

	public int getApprovalResult1() {
		return approvalResult1;
	}

	public void setApprovalResult1(int approvalResult1) {
		this.approvalResult1 = approvalResult1;
	}

	public String getApprovalOpinion1() {
		return approvalOpinion1;
	}

	public void setApprovalOpinion1(String approvalOpinion1) {
		this.approvalOpinion1 = approvalOpinion1;
	}

	public String getApprovalID2() {
		return approvalID2;
	}

	public void setApprovalID2(String approvalID2) {
		this.approvalID2 = approvalID2;
	}

	public int getApprovalResult2() {
		return approvalResult2;
	}

	public void setApprovalResult2(int approvalResult2) {
		this.approvalResult2 = approvalResult2;
	}

	public String getApprovalOpinion2() {
		return approvalOpinion2;
	}

	public void setApprovalOpinion2(String approvalOpinion2) {
		this.approvalOpinion2 = approvalOpinion2;
	}

	public String getApprovalID3() {
		return approvalID3;
	}

	public void setApprovalID3(String approvalID3) {
		this.approvalID3 = approvalID3;
	}

	public int getApprovalResult3() {
		return approvalResult3;
	}

	public void setApprovalResult3(int approvalResult3) {
		this.approvalResult3 = approvalResult3;
	}

	public String getApprovalOpinion3() {
		return approvalOpinion3;
	}

	public void setApprovalOpinion3(String approvalOpinion3) {
		this.approvalOpinion3 = approvalOpinion3;
	}



	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}

	public String getApprovalName() {
		return approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	public int getApprovalID() {
		return approvalID;
	}

	public void setApprovalID(int approvalID) {
		this.approvalID = approvalID;
	}

	public String getApprovalTime1() {
		return approvalTime1;
	}

	public void setApprovalTime1(String approvalTime1) {
		this.approvalTime1 = approvalTime1;
	}

	public String getApprovalTime2() {
		return approvalTime2;
	}

	public void setApprovalTime2(String approvalTime2) {
		this.approvalTime2 = approvalTime2;
	}

	public String getApprovalTime3() {
		return approvalTime3;
	}

	public void setApprovalTime3(String approvalTime3) {
		this.approvalTime3 = approvalTime3;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



	
}
