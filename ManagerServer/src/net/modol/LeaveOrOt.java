package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class LeaveOrOt implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int leaveOrOtID;
	private String leaveOrOtReason;
	private String leaveOrOtDate;
	private Date beginDate;
	private int lastDate;
	private Date endDate;
	private JcxxYhEN user;
	private String num;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public JcxxYhEN getUser() {
		return user;
	}

	public void setUser(JcxxYhEN user) {
		this.user = user;
	}

	public void setApprovalID1(JcxxYhEN approvalID1) {
		this.approvalID1 = approvalID1;
	}
	//@JsonIgnore
	private JcxxYhEN approvalID1;

	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}

	private int approvalResult1;
	private String approvalOpinion1;
	private String approvalTime1;
	private int approvalID2;
	private int approvalResult2;
	private String approvalOpinion2;
	private String approvalTime2;
	private int approvalID3;
	private int approvalResult3;
	private String approvalOpinion3;
	private String approvalTime3;
	private String approvalResult;
	private String address;
	private LeaveOrOtKind leaveOrOtKind;




	public LeaveOrOtKind getLeaveOrOtKind() {
		return leaveOrOtKind;
	}

	public void setLeaveOrOtKind(LeaveOrOtKind leaveOrOtKind) {
		this.leaveOrOtKind = leaveOrOtKind;
	}

	public int getLeaveOrOtID() {
		return leaveOrOtID;
	}
	public void setLeaveOrOtID(int leaveOrOtID) {
		this.leaveOrOtID = leaveOrOtID;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public int getLastDate() {
		return lastDate;
	}
	public void setLastDate(int lastDate) {
		this.lastDate = lastDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public JcxxYhEN getApprovalID1() {
		return approvalID1;
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
	public int getApprovalID2() {
		return approvalID2;
	}
	public void setApprovalID2(int approvalID2) {
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
	public int getApprovalID3() {
		return approvalID3;
	}
	public void setApprovalID3(int approvalID3) {
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
