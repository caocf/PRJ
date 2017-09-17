package com.highwaycenter.bean;

public class ModuleState {

	private int moduletype;//模块类型
	private String modulename;//模块名字
	private int currentstate;//模块状态   0:同步成功，1：正在同步，-1：同步失败
	private String lasttime;//上次同步时间
	private int laststate;//上次同步状态
	
	private String message;//显示信息
	public ModuleState(){
		
	}
	public ModuleState(int moduletype,String modulename){
		this.moduletype = moduletype;
		this.modulename = modulename;
	}
	
	public ModuleState(int moduletype,String modulename,int currentstate){
		this.moduletype = moduletype;
		this.modulename = modulename;
		this.currentstate =currentstate;
	}
	
	public int getModuletype() {
		return moduletype;
	}
	public void setModuletype(int moduletype) {
		this.moduletype = moduletype;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public int getCurrentstate() {
		return currentstate;
	}
	public void setCurrentstate(int currentstate) {
		this.currentstate = currentstate;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	public int getLaststate() {
		return laststate;
	}
	public void setLaststate(int laststate) {
		this.laststate = laststate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
