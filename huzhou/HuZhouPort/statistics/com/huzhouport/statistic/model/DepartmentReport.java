package com.huzhouport.statistic.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepartmentReport {
	private float count;//数量
	private String firstName;//一类名称
	private String secondName;//二类名称
	private String startTime;
	private String toTime;
	private int departmentId;
	private String deparmentName;
	
	List<HashMap<String,Object>> list_person = new ArrayList<HashMap<String,Object>>();//个人数据
	List<HashMap<String,Object>> list_department = new ArrayList<HashMap<String,Object>>();//合计数据
	public float getCount() {
		return count;
	}
	public void setCount(float count) {
		this.count = count;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getDeparmentName() {
		return deparmentName;
	}
	public void setDeparmentName(String deparmentName) {
		this.deparmentName = deparmentName;
	}
	public List<HashMap<String, Object>> getList_person() {
		return list_person;
	}
	public void setList_person(List<HashMap<String, Object>> listPerson) {
		list_person = listPerson;
	}
	public List<HashMap<String, Object>> getList_department() {
		return list_department;
	}
	public void setList_department(List<HashMap<String, Object>> listDepartment) {
		list_department = listDepartment;
	}
	
}
