package com.huzhouport.common.action;


import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,
		RequestAware {

	private static final long serialVersionUID = 1L;
	protected Map<String, Object> request;
	protected Map<String, Object> session;
	private String cpage = "1";

	private long now;
	public String getCpage() {
		return cpage;
	}

	public void setCpage(String cpage) {
		this.cpage = cpage;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;

	}

	
	protected int DataBegin(int cpage,int size, int pageSize) {
		int pages = 0;//1表中总页数
		int lastSize = 0;//1表中最后一页数据条数
		int begin = 0;//2表从几条开始取数据
		lastSize= size % pageSize;
		if (lastSize== 0) {
			pages = size / pageSize;
		} else {
			pages = (size / pageSize) + 1;			
		}
		begin =(cpage-pages)*(pageSize-lastSize);
		return begin;
	}

	public String takeDateInfo(){
		now=new Date().getTime();	
		return SUCCESS;
	}

	public void setNow(long now)
	{
		this.now = now;
	}
	public long getNow()
	{
		return now;
	}
}
