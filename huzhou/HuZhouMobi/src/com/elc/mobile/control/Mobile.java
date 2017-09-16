package com.elc.mobile.control;

/**
 * 手机信息类
 * 
 * 如mobileName == "" and androidName == "" 支持所有android版本、所有手机
 * 如mobileName == "" and androidName != "" 支持此手机型号下的所有android版本
 * 如mobileName != "" and androidName == "" 支持此android版本下的所有手机
 * 
 * 
 * @author Administrator
 *
 */
public class Mobile 
{
	int priority;						//处理优先级
	String mobileName;					//手机型号
	String androidName;					//手机android版本号
	
	public String getMobileName() {
		return mobileName;
	}
	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
	}
	public String getAndroidName() {
		return androidName;
	}
	public void setAndroidName(String androidName) {
		this.androidName = androidName;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
