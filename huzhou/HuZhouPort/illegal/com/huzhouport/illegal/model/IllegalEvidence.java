package com.huzhouport.illegal.model;

import java.io.File;
import java.util.List;

public class IllegalEvidence  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int evidenceId;
	private String evidenceName;
	private int evidenceKind;
	private String evidencePath;
	private String evidenceMD5;
	
	private List<File> ef;//附件列表
	private List<String> efFileName;
	private List<String> efContentType;
	
	public int getEvidenceId() {
		return evidenceId;
	}
	public void setEvidenceId(int evidenceId) {
		this.evidenceId = evidenceId;
	}
	public String getEvidenceName() {
		return evidenceName;
	}
	public void setEvidenceName(String evidenceName) {
		this.evidenceName = evidenceName;
	}
	public int getEvidenceKind() {
		return evidenceKind;
	}
	public void setEvidenceKind(int evidenceKind) {
		this.evidenceKind = evidenceKind;
	}
	public String getEvidencePath() {
		return evidencePath;
	}
	public void setEvidencePath(String evidencePath) {
		this.evidencePath = evidencePath;
	}
	public String getEvidenceMD5() {
		return evidenceMD5;
	}
	public void setEvidenceMD5(String evidenceMD5) {
		this.evidenceMD5 = evidenceMD5;
	}
	public List<File> getEf() {
		return ef;
	}
	public void setEf(List<File> ef) {
		this.ef = ef;
	}
	public List<String> getEfFileName() {
		return efFileName;
	}
	public void setEfFileName(List<String> efFileName) {
		this.efFileName = efFileName;
	}
	public List<String> getEfContentType() {
		return efContentType;
	}
	public void setEfContentType(List<String> efContentType) {
		this.efContentType = efContentType;
	}
	
	

}
