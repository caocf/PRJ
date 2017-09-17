package com.highwaycenter.data.model;

import java.util.Date;

public class Snapshot implements java.io.Serializable {

	private static final long serialVersionUID = 6306733671263231866L;
	private Integer id	;
	private Date date	;
	private String transName;	
	private String stepName	;
	private Integer stepCopy	;
	private Integer totalLinesRead	;
	private Integer totalLinesWritten	;
	private Integer totalLinesInput	;
	private Integer totalLinesOutput	;
	private Integer totalLinesUpdated	;
	private Integer totalLinesRejected	;
	private Integer totalErrors	;
	private Integer timeDifference	;
	private Integer linesRead	;
	private Integer linesWritten	;
	private Integer linesInput	;
	private Integer linesOutput	;
	private Integer linesUpdated	;
	private Integer linesRejected	;
	private Integer errors	;
	private Integer inputBufferSize	;
	private Integer outputBufferSize	;
	private String  datestr;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public Integer getStepCopy() {
		return stepCopy;
	}
	public void setStepCopy(Integer stepCopy) {
		this.stepCopy = stepCopy;
	}
	public Integer getTotalLinesRead() {
		return totalLinesRead;
	}
	public void setTotalLinesRead(Integer totalLinesRead) {
		this.totalLinesRead = totalLinesRead;
	}
	public Integer getTotalLinesWritten() {
		return totalLinesWritten;
	}
	public void setTotalLinesWritten(Integer totalLinesWritten) {
		this.totalLinesWritten = totalLinesWritten;
	}
	public Integer getTotalLinesInput() {
		return totalLinesInput;
	}
	public void setTotalLinesInput(Integer totalLinesInput) {
		this.totalLinesInput = totalLinesInput;
	}
	public Integer getTotalLinesOutput() {
		return totalLinesOutput;
	}
	public void setTotalLinesOutput(Integer totalLinesOutput) {
		this.totalLinesOutput = totalLinesOutput;
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
	public Integer getTotalErrors() {
		return totalErrors;
	}
	public void setTotalErrors(Integer totalErrors) {
		this.totalErrors = totalErrors;
	}
	public Integer getTimeDifference() {
		return timeDifference;
	}
	public void setTimeDifference(Integer timeDifference) {
		this.timeDifference = timeDifference;
	}
	public Integer getLinesRead() {
		return linesRead;
	}
	public void setLinesRead(Integer linesRead) {
		this.linesRead = linesRead;
	}
	public Integer getLinesWritten() {
		return linesWritten;
	}
	public void setLinesWritten(Integer linesWritten) {
		this.linesWritten = linesWritten;
	}
	public Integer getLinesInput() {
		return linesInput;
	}
	public void setLinesInput(Integer linesInput) {
		this.linesInput = linesInput;
	}
	public Integer getLinesOutput() {
		return linesOutput;
	}
	public void setLinesOutput(Integer linesOutput) {
		this.linesOutput = linesOutput;
	}
	public Integer getLinesUpdated() {
		return linesUpdated;
	}
	public void setLinesUpdated(Integer linesUpdated) {
		this.linesUpdated = linesUpdated;
	}
	public Integer getLinesRejected() {
		return linesRejected;
	}
	public void setLinesRejected(Integer linesRejected) {
		this.linesRejected = linesRejected;
	}
	public Integer getErrors() {
		return errors;
	}
	public void setErrors(Integer errors) {
		this.errors = errors;
	}
	public Integer getInputBufferSize() {
		return inputBufferSize;
	}
	public void setInputBufferSize(Integer inputBufferSize) {
		this.inputBufferSize = inputBufferSize;
	}
	public Integer getOutputBufferSize() {
		return outputBufferSize;
	}
	public void setOutputBufferSize(Integer outputBufferSize) {
		this.outputBufferSize = outputBufferSize;
	}
	public String getDatestr() {
		return datestr;
	}
	public void setDatestr(String datestr) {
		this.datestr = datestr;
	}
 
	
	
	
	
}
