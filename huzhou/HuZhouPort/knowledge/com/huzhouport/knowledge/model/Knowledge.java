package com.huzhouport.knowledge.model;

import java.util.Date;

public class Knowledge implements java.io.Serializable{
 private int knowledgeID;
 private String knowledgeName;
 private String knowledgeIndex;
 private int partOfKind;
 private int isLink;
 private int isLinkInfo;
 private String knowledgeContent;
 private Date date;						//通知日期
 
 private String userids;				//所属用户id， 如果是多用户，则以","分隔
 private String usernames;				//通知所属用户名
 
 public void setDate(Date date) {
	this.date = date;
}
 public Date getDate() {
	return date;
}
 public String getUsernames() {
	return usernames;
}
 public void setUsernames(String usernames) {
	this.usernames = usernames;
}
 public void setUserids(String userids) {
	this.userids = userids;
}
 public String getUserids() {
	return userids;
}
 
public int getIsLinkInfo() {
	return isLinkInfo;
}
public void setIsLinkInfo(int isLinkInfo) {
	this.isLinkInfo = isLinkInfo;
}
public int getKnowledgeID() {
	return knowledgeID;
}
public void setKnowledgeID(int knowledgeID) {
	this.knowledgeID = knowledgeID;
}
public String getKnowledgeName() {
	return knowledgeName;
}
public void setKnowledgeName(String knowledgeName) {
	this.knowledgeName = knowledgeName;
}
public String getKnowledgeIndex() {
	return knowledgeIndex;
}
public void setKnowledgeIndex(String knowledgeIndex) {
	this.knowledgeIndex = knowledgeIndex;
}
public int getPartOfKind() {
	return partOfKind;
}
public void setPartOfKind(int partOfKind) {
	this.partOfKind = partOfKind;
}
public int getIsLink() {
	return isLink;
}
public void setIsLink(int isLink) {
	this.isLink = isLink;
}
public String getKnowledgeContent() {
	return knowledgeContent;
}
public void setKnowledgeContent(String knowledgeContent) {
	this.knowledgeContent = knowledgeContent;
}
 
}
