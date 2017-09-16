package com.huzhouport.organization.model;

import java.util.List;

public class Department implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int departmentId;
	private String departmentName;
	private int partOfDepartmentId;
	private String refDepartID;//部门省局编号
	private List<Object> listDepartment;//用于存储本部门下面的所有子部门
	
	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getPartOfDepartmentId() {
		return partOfDepartmentId;
	}

	public void setPartOfDepartmentId(int partOfDepartmentId) {
		this.partOfDepartmentId = partOfDepartmentId;
	}

	public List<Object> getListDepartment() {
		return listDepartment;
	}

	public void setListDepartment(List<Object> listDepartment) {
		this.listDepartment = listDepartment;
	}

	public String getRefDepartID() {
		return refDepartID;
	}

	public void setRefDepartID(String refDepartID) {
		this.refDepartID = refDepartID;
	}

}