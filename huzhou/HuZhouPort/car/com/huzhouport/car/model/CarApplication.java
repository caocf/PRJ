package com.huzhouport.car.model;

import java.util.Date;

public class CarApplication
{
	int id;
	Date aptime;
	int userid;
	Date starttime;
	Date endtime;
	int number;
	String location;
	String target;
	Integer isread;	
	
	public Integer getIsread() {
		return isread;
	}
	public void setIsread(Integer isread) {
		this.isread = isread;
	}
	public Date getAptime() {
		return aptime;
	}
	public void setAptime(Date aptime) {
		this.aptime = aptime;
	}
	String reason;
	String tel;
	
	int approver1id;
	int approver2id;
	int status1id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getApprover1id() {
		return approver1id;
	}
	public void setApprover1id(int approver1id) {
		this.approver1id = approver1id;
	}
	public int getApprover2id() {
		return approver2id;
	}
	public void setApprover2id(int approver2id) {
		this.approver2id = approver2id;
	}
	public int getStatus1id() {
		return status1id;
	}
	public void setStatus1id(int status1id) {
		this.status1id = status1id;
	}
	public int getStatus2id() {
		return status2id;
	}
	public void setStatus2id(int status2id) {
		this.status2id = status2id;
	}
	public String getMark1() {
		return mark1;
	}
	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}
	public String getMark2() {
		return mark2;
	}
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}
	public int getAssignmentid() {
		return assignmentid;
	}
	public void setAssignmentid(int assignmentid) {
		this.assignmentid = assignmentid;
	}
	int status2id;
	String mark1;
	String mark2;
	int assignmentid; 
	
	Date aptime1;
	Date aptime2;
	public Date getAptime1() {
		return aptime1;
	}
	public void setAptime1(Date aptime1) {
		this.aptime1 = aptime1;
	}
	public Date getAptime2() {
		return aptime2;
	}
	public void setAptime2(Date aptime2) {
		this.aptime2 = aptime2;
	}
}
