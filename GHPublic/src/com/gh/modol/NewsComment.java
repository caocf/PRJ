package com.gh.modol;

public class NewsComment {
	/*
	 * 新闻评论
	 * */
	private Long id;
	private String content;//评论内容
	private String sumbtime;//评论时间
	private String newsid;//新闻id
	private String username;//用户名
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSumbtime() {
		return sumbtime;
	}
	public void setSumbtime(String sumbtime) {
		this.sumbtime = sumbtime;
	}
	public String getNewsid() {
		return newsid;
	}
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
