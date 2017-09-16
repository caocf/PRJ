package com.huzhouport.wechat.model;

import java.util.Date;

import com.huzhouport.common.model.QueryCondition;



public class Message implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int messageID;
	private int userFrom;
	private int userTo;
	private int messageKind;
	private String messageContent;
	private Date messageTime;
	private Date readTime;
	private QueryCondition queryCondition=new QueryCondition();
	
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	public int getMessageID() {
		return messageID;
	}
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public int getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(int userFrom) {
		this.userFrom = userFrom;
	}
	public int getUserTo() {
		return userTo;
	}
	public void setUserTo(int userTo) {
		this.userTo = userTo;
	}
	public int getMessageKind() {
		return messageKind;
	}
	public void setMessageKind(int messageKind) {
		this.messageKind = messageKind;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public Date getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

}
