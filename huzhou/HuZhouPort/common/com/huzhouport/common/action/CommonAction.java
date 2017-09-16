package com.huzhouport.common.action;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private String serverTime;
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	public String getServerTime() {
		return serverTime;
	}
	public String GetServiceTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		serverTime=df.format(new Date()).toString();
		return SUCCESS;
	}

}
