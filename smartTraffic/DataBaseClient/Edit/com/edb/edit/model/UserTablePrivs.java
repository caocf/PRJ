package com.edb.edit.model;

/* 表的权限
 * 沈丹丹
 * */
import java.io.Serializable;

public class UserTablePrivs implements Serializable {
	private static final long serialVersionUID = 1L;
	private String grantee;
	private String tableName;
	private String privailege;
	public String getGrantee() {
		return grantee;
	}
	public void setGrantee(String grantee) {
		this.grantee = grantee;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPrivailege() {
		return privailege;
	}
	public void setPrivailege(String privailege) {
		this.privailege = privailege;
	}
	
}
