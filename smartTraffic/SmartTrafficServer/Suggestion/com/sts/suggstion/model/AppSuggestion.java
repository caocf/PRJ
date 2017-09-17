package com.sts.suggstion.model;

public class AppSuggestion
{
	int suggestionid;
	int userid;
	String title;
	String content;
	
	public int getSuggestionid() {
		return suggestionid;
	}
	public void setSuggestionid(int suggestionid) {
		this.suggestionid = suggestionid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
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
}
