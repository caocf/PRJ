package com.sts.news.model;


/**
 * 新闻列表
 * @author Administrator
 *
 */
public class NewsForPhone 
{
	private int id;						//新闻id
	private int type;					//新闻主类型
	private int subType;				//新闻子类型
	private String title;				//标题
	private String content;				//简介
	private String date;					//新闻日期
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getSubType() {
		return subType;
	}
	public void setSubType(int subType) {
		this.subType = subType;
	}
	
}
