package com.highwaycenter.bean;

public class QxNodes  implements java.io.Serializable {

	private static final long serialVersionUID = -5515421236638485208L;
	private int qxnodesId;  // 基本权限编号
	private int qxzqxdyId;  //权限组权限对应编号
	private String qxmc;   //权限名称
	private int type;     //  
	private int fqxzId;   //父权限组编号
	
	public QxNodes( int qxzqxdyId,int qxnodesId, String qxmc,int fqxzId){
		this.qxmc = qxmc;
	    this.qxzqxdyId = qxzqxdyId;
	    this.qxnodesId = qxnodesId;
	    this.fqxzId = fqxzId;
	}
	
	public QxNodes(){
		
	}
	
	public int getQxnodesId() {
		return qxnodesId;
	}
	public void setQxnodesId(int qxnodesId) {
		this.qxnodesId = qxnodesId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getQxzqxdyId() {
		return qxzqxdyId;
	}
	public void setQxzqxdyId(int qxzqxdyId) {
		this.qxzqxdyId = qxzqxdyId;
	}
	public String getQxmc() {
		return qxmc;
	}
	public void setQxmc(String qxmc) {
		this.qxmc = qxmc;
	}

	public int getFqxzId() {
		return fqxzId;
	}

	public void setFqxzId(int fqxzId) {
		this.fqxzId = fqxzId;
	}

}
