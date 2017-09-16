package com.huzhouport.illegal.model;

public class IllegalEvidenceLink implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
	private int illegalId;
	private int evidenceId;
	
	public int getIllegalId() {
		return illegalId;
	}
	public void setIllegalId(int illegalId) {
		this.illegalId = illegalId;
	}
	public int getEvidenceId() {
		return evidenceId;
	}
	public void setEvidenceId(int evidenceId) {
		this.evidenceId = evidenceId;
	}

	
}
