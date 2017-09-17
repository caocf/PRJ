package com.highwaycenter.data.model;

import java.util.List;

import javax.persistence.Transient;

public class HSnopeLog implements java.io.Serializable {

	private static final Long serialVersionUID = -3856585164522628509L;

	private Integer jobid;//同步编号
	private String jobname;//同步名称  
	private String status;//同步状态  0:同步成功，1：正在同步；-1：同步失败
	private Long totallineread;//读总行数
	private Long totallinewritten;//写总数据
	private Long totallineupdate;//更新总数据
	private Long totallinerejected;//舍弃总行数
	private Long errors;//错误总行数
	private String startdate;//开始时间
	private String enddate;//结束时间
	private String translog;//转换记录
	private String steplog;//步骤记录
	
	@Transient
	private List<HSnopeTransLog> transloglist;
	
	public HSnopeLog() {
		super();
	}
	
	
	public HSnopeLog(String jobname, String status, Long totallineread,
			Long totallinewritten, Long totallineupdate,
			Long totallinerejected, Long errors, String startdate) {
		super();
		this.jobname = jobname;
		this.status = status;
		this.totallineread = totallineread;
		this.totallinewritten = totallinewritten;
		this.totallineupdate = totallineupdate;
		this.totallinerejected = totallinerejected;
		this.errors = errors;
		this.startdate = startdate;
	}


	public HSnopeLog(int jobid, String jobname, String status,
			Long totallineread, Long totallinewritten, Long totallineupdate,
			Long totallinerejected, Long errors, String startdate,
			String enddate, String translog, String steplog) {
		super();
		this.jobid = jobid;
		this.jobname = jobname;
		this.status = status;
		this.totallineread = totallineread;
		this.totallinewritten = totallinewritten;
		this.totallineupdate = totallineupdate;
		this.totallinerejected = totallinerejected;
		this.errors = errors;
		this.startdate = startdate;
		this.enddate = enddate;
		this.translog = translog;
		this.steplog = steplog;
	}
	public Integer getJobid() {
		return jobid;
	}
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getTotallineread() {
		return totallineread;
	}
	public void setTotallineread(Long totallineread) {
		this.totallineread = totallineread;
	}
	public Long getTotallinewritten() {
		return totallinewritten;
	}
	public void setTotallinewritten(Long totallinewritten) {
		this.totallinewritten = totallinewritten;
	}
	public Long getTotallineupdate() {
		return totallineupdate;
	}
	public void setTotallineupdate(Long totallineupdate) {
		this.totallineupdate = totallineupdate;
	}
	public Long getTotallinerejected() {
		return totallinerejected;
	}
	public void setTotallinerejected(Long totallinerejected) {
		this.totallinerejected = totallinerejected;
	}
	public Long getErrors() {
		return errors;
	}
	public void setErrors(Long errors) {
		this.errors = errors;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getTranslog() {
		return translog;
	}
	public void setTranslog(String translog) {
		this.translog = translog;
	}
	public String getSteplog() {
		return steplog;
	}
	public void setSteplog(String steplog) {
		this.steplog = steplog;
	}


	public List<HSnopeTransLog> getTransloglist() {
		return transloglist;
	}


	public void setTransloglist(List<HSnopeTransLog> transloglist) {
		this.transloglist = transloglist;
	}
	
	
}
