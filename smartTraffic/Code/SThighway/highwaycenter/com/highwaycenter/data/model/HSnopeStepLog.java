package com.highwaycenter.data.model;

import java.sql.Timestamp;

public class HSnopeStepLog {
	private Integer idbatch;
	private String channelid;
	private Timestamp logdate;
	private String transname;
	private String stepname;
	private Integer stepcopy;
	private  Long readline;
	private Long writtenline;
	private Long updateline;
	private Long inputline;
	private Long outputline;
	private Long rejectline;
	private Long errorsline;
	
	public HSnopeStepLog(){
		
	}

	
	
	
	public HSnopeStepLog(Timestamp logdate,
			String transname, String stepname, Long readline,
			Long writtenline, Long updateline,
			Long errorsline) {
		super();

		this.logdate = logdate;
		this.transname = transname;
		this.stepname = stepname;
		this.readline = readline;
		this.writtenline = writtenline;
		this.updateline = updateline;

		this.errorsline = errorsline;
	}



	public Integer getIdbatch() {
		return idbatch;
	}

	public void setIdbatch(Integer idbatch) {
		this.idbatch = idbatch;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public Timestamp getLogdate() {
		return logdate;
	}

	public void setLogdate(Timestamp logdate) {
		this.logdate = logdate;
	}

	public String getTransname() {
		return transname;
	}

	public void setTransname(String tansname) {
		this.transname = tansname;
	}

	public String getStepname() {
		return stepname;
	}

	public void setStepname(String stepname) {
		this.stepname = stepname;
	}

	public Integer getStepcopy() {
		return stepcopy;
	}

	public void setStepcopy(Integer stepcopy) {
		this.stepcopy = stepcopy;
	}

	public Long getReadline() {
		return readline;
	}

	public void setReadline(Long readline) {
		this.readline = readline;
	}

	public Long getWrittenline() {
		return writtenline;
	}

	public void setWrittenline(Long writtenline) {
		this.writtenline = writtenline;
	}

	public Long getUpdateline() {
		return updateline;
	}

	public void setUpdateline(Long updateline) {
		this.updateline = updateline;
	}

	public Long getInputline() {
		return inputline;
	}

	public void setInputline(Long inputline) {
		this.inputline = inputline;
	}

	public Long getOutputline() {
		return outputline;
	}

	public void setOutputline(Long outputline) {
		this.outputline = outputline;
	}

	public Long getRejectline() {
		return rejectline;
	}

	public void setRejectline(Long rejectline) {
		this.rejectline = rejectline;
	}

	public Long getErrorsline() {
		return errorsline;
	}

	public void setErrorsline(Long errorsline) {
		this.errorsline = errorsline;
	}
	


	

}
