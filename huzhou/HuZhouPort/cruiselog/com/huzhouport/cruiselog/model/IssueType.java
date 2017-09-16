package com.huzhouport.cruiselog.model;

public class IssueType implements java.io.Serializable
{
	int id;
	String name;
	int level;
	int pid;
	String pname;
	int haschild;
	
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getHaschild() {
		return haschild;
	}
	public void setHaschild(int haschild) {
		this.haschild = haschild;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}	
	
}
