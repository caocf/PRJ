package com.highwaycenter.data.model;

import java.sql.Timestamp;
import java.util.List;

public class HSnopeTransLog {
	private Integer idbatch;
	private String channelid;//转换记录的CHANNEL_Id
	private String transname;
	private String status;     //转换状态
	private Long readline;//读的数据
	private Long writeline;//写的数据
	private Long updateline;//更新的数据
	private Long inputline;//输入的数据
	private Long outputline;//输出的数据
	private Long rejectline;//舍弃的数据
	private Long errorsline;//发生的错误
	private Timestamp startdate;
	private Timestamp enddate;
	private Timestamp logdate;
	private Timestamp depdate;
	private Timestamp replaydate;
	private Integer jobid;         //对应的jobid
	

	private List<HSnopeStepLog> steploglist;

	private String status_show;//显示的状态

	private String startdate_show;  //在数据库中是ENDDATE

	private String enddate_show;    //在数据库中是LOGDATE
    
	public HSnopeTransLog(){
		
	}
	
	public HSnopeTransLog(String transname,Long readline,
			Long writeline, Long updateline, 
			Long errorsline, Integer jobid,
			List<HSnopeStepLog> steploglist,
			String startdate_show, String enddate_show) {
		super();
		this.transname = transname;
		this.readline = readline;
		this.writeline = writeline;
		this.updateline = updateline;
		this.errorsline = errorsline;
		this.jobid = jobid;
		this.steploglist = steploglist;
		this.startdate_show = startdate_show;
		this.enddate_show = enddate_show;
	}

	public HSnopeTransLog(String channelid, Long errorsline) {
		super();
		this.channelid = channelid;
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

	public String getTransname() {
		return transname;
	}

	public void setTransname(String transname) {
		this.transname = transname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	

	public Long getReadline() {
		return readline;
	}
	public void setReadline(Long readline) {
		this.readline = readline;
	}
	public Long getWriteline() {
		return writeline;
	}
	public void setWriteline(Long writeline) {
		this.writeline = writeline;
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
	public Timestamp getStartdate() {
		return startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getEnddate() {
		return enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public Timestamp getLogdate() {
		return logdate;
	}

	public void setLogdate(Timestamp logdate) {
		this.logdate = logdate;
	}

	public Timestamp getDepdate() {
		return depdate;
	}

	public void setDepdate(Timestamp depdate) {
		this.depdate = depdate;
	}

	public Timestamp getReplaydate() {
		return replaydate;
	}

	public void setReplaydate(Timestamp replaydate) {
		this.replaydate = replaydate;
	}

	public Integer getJobid() {
		return jobid;
	}

	public void setJobid(Integer jobid) {
		this.jobid = jobid;
	}

	public List<HSnopeStepLog> getSteploglist() {
		return steploglist;
	}

	public void setSteploglist(List<HSnopeStepLog> steploglist) {
		this.steploglist = steploglist;
	}

	public String getStatus_show() {
		return status_show;
	}

	public void setStatus_show(String status_show) {
		this.status_show = status_show;
	}

	public String getStartdate_show() {
		return startdate_show;
	}

	public void setStartdate_show(String startdate_show) {
		this.startdate_show = startdate_show;
	}

	public String getEnddate_show() {
		return enddate_show;
	}

	public void setEnddate_show(String enddate_show) {
		this.enddate_show = enddate_show;
	}





	
}
