package com.gh.modol;

import java.util.List;

public class News {
	/*
	 * 新闻
	 * */
	
	private String id;//id
	private String title;
	private String content;//新闻主体
	private String updatetime;//更新时间
	private String region;//新闻类型
	private String newstype;//
	private String filedir;
	private String imdir;
	private List<Comments> comments;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getNewstype() {
		return newstype;
	}
	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}
	public String getFiledir() {
		return filedir;
	}
	public void setFiledir(String filedir) {
		this.filedir = filedir;
	}
	public String getImdir() {
		return imdir;
	}
	public void setImdir(String imdir) {
		this.imdir = imdir;
	}
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	
}
