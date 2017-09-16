package com.huzhouport.leaveandovertime.service.impl;

import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;

public class LeaveOrOtKindBeanWithStatus extends LeaveOrOtKindbean {
	private int msgstatus;

	public LeaveOrOtKindBeanWithStatus(LeaveOrOtKindbean bean) {
		super.setAddress(bean.getAddress());
		super.setApprovalID(bean.getApprovalID());
		super.setApprovalID1(bean.getApprovalID1());
		super.setApprovalID2(bean.getApprovalID2());
		super.setApprovalID3(bean.getApprovalID3());
		super.setApprovalName(bean.getApprovalName());
		super.setApprovalOpinion1(bean.getApprovalOpinion1());
		super.setApprovalOpinion2(bean.getApprovalOpinion2());
		super.setApprovalOpinion3(bean.getApprovalOpinion3());
		super.setApprovalResult(bean.getApprovalResult());
		super.setApprovalResult1(bean.getApprovalResult1());
		super.setApprovalResult2(bean.getApprovalResult2());
		super.setApprovalResult3(bean.getApprovalResult3());
		super.setApprovalTime1(bean.getApprovalTime1());
		super.setApprovalTime2(bean.getApprovalTime2());
		super.setApprovalTime3(bean.getApprovalTime3());
		super.setBeginDate(bean.getBeginDate());
		super.setDefauleDate(bean.getDefauleDate());
		super.setEndDate(bean.getEndDate());
		super.setKindID(bean.getKindID());
		super.setKindName(bean.getKindName());
		super.setKindType(bean.getKindType());
		super.setLastDate(bean.getLastDate());
		super.setLeaveOrOtDate(bean.getLeaveOrOtDate());
		super.setLeaveOrOtID(bean.getLeaveOrOtID());
		super.setLeaveOrOtKind(bean.getLeaveOrOtKind());
		super.setLeaveOrOtReason(bean.getLeaveOrOtReason());
		super.setLeaveOrOtUser(bean.getLeaveOrOtUser());
	}

	public int getMsgstatus() {
		return msgstatus;
	}

	public void setMsgstatus(int msgstatus) {
		this.msgstatus = msgstatus;
	}
}
