package com.huzhouport.knowledge.model;

public class KnowledgeKind implements java.io.Serializable {
	
	private int kindID;
	private String kindName;
	private int kindIndex;
	public int getKindID() {
		return kindID;
	}
	public void setKindID(int kindID) {
		this.kindID = kindID;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public int getKindIndex() {
		return kindIndex;
	}
	public void setKindIndex(int kindIndex) {
		this.kindIndex = kindIndex;
	}

	

}
