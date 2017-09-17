package com.highwaycenter.data.model;

public class SnapshotRecord implements java.io.Serializable {

	private static final long serialVersionUID = -55636419235976617L;
	private Integer recordbh; //记录编号
	private Integer modulebh;//模块编号
	private String modulename;//模块名称
	private Integer totalLinesRead;
	private Integer totalLinesWritten;//同步写入数据
	private Integer totalLinesUpdated;//同步更新数据
	private Integer totalLinesRejected;//同步失败数据
	private String finishtime;//同步结束时间
	
	
	
	public Integer getTotalLinesRead() {
		return totalLinesRead;
	}
	public void setTotalLinesRead(Integer totalLinesRead) {
		this.totalLinesRead = totalLinesRead;
	}
	public Integer getRecordbh() {
		return recordbh;
	}
	public void setRecordbh(Integer recordbh) {
		this.recordbh = recordbh;
	}
	public Integer getModulebh() {
		return modulebh;
	}
	public void setModulebh(Integer modulebh) {
		this.modulebh = modulebh;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public Integer getTotalLinesWritten() {
		return totalLinesWritten;
	}
	public void setTotalLinesWritten(Integer totalLinesWritten) {
		this.totalLinesWritten = totalLinesWritten;
	}
	public Integer getTotalLinesUpdated() {
		return totalLinesUpdated;
	}
	public void setTotalLinesUpdated(Integer totalLinesUpdated) {
		this.totalLinesUpdated = totalLinesUpdated;
	}
	public Integer getTotalLinesRejected() {
		return totalLinesRejected;
	}
	public void setTotalLinesRejected(Integer totalLinesRejected) {
		this.totalLinesRejected = totalLinesRejected;
	}
	public String getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}
	
	
	
	
	
	
	
	

}
