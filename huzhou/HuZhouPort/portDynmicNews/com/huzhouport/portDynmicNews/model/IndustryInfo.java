package com.huzhouport.portDynmicNews.model;

import java.util.Date;

//行业资讯
public class IndustryInfo {
	private int id;// id
	private String title;// 标题
	private String content;// 内容
	private Date updatetime;// 添加时间
	private int oriobj;// 面向对象

	public static int oriobj_public = 0;// 外部
	public static int oriobj_inner = 1;// 内部
	public static int oriobj_both = 2;// 全部

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getOriobj() {
		return oriobj;
	}

	public void setOriobj(int oriobj) {
		this.oriobj = oriobj;
	}
}
