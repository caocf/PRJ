package com.huzhouport.illegal.model;

import com.huzhouport.common.model.QueryCondition;

public class Illegal implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int illegalId;
	private int enforecers1;
	private int enforecers2;
	private int illegalLocation;
	private int illegalCategories;//1:港政2：运政3：航政4：海事
	private int reviewWether;//0待审核，1审核
	private int reviewUser;//审核人ID
	private String illegalObject;//违章对象 max:100
	private int illegalReason;//违章缘由 max:500
	private String illegalContent;//违章描述max2000
	private String illegalTime;
	private int reviewResult;//1通过2未通过
	private String reviewComment;
	private String reviewTime;//审核时间
	private int isPost;////0未上传到综合数据库，1已上传到未上传到综合数据库
	private QueryCondition queryCondition=new QueryCondition();
	
	private int status;//只用于返回推送消息状态数据
	
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	
	public int getEnforecers1() {
		return enforecers1;
	}
	public void setEnforecers1(int enforecers1) {
		this.enforecers1 = enforecers1;
	}
	public int getEnforecers2() {
		return enforecers2;
	}
	public void setEnforecers2(int enforecers2) {
		this.enforecers2 = enforecers2;
	}
	public int getIllegalLocation() {
		return illegalLocation;
	}
	public void setIllegalLocation(int illegalLocation) {
		this.illegalLocation = illegalLocation;
	}

	public int getReviewWether() {
		return reviewWether;
	}
	public void setReviewWether(int reviewWether) {
		this.reviewWether = reviewWether;
	}
	public int getReviewUser() {
		return reviewUser;
	}
	public void setReviewUser(int reviewUser) {
		this.reviewUser = reviewUser;
	}
	public String getIllegalObject() {
		return illegalObject;
	}
	public void setIllegalObject(String illegalObject) {
		this.illegalObject = illegalObject;
	}
	
	public int getIllegalId() {
		return illegalId;
	}
	public void setIllegalId(int illegalId) {
		this.illegalId = illegalId;
	}
	public int getIllegalCategories() {
		return illegalCategories;
	}
	public void setIllegalCategories(int illegalCategories) {
		this.illegalCategories = illegalCategories;
	}
	public int getIllegalReason() {
		return illegalReason;
	}
	public void setIllegalReason(int illegalReason) {
		this.illegalReason = illegalReason;
	}
	public String getIllegalContent() {
		return illegalContent;
	}
	public void setIllegalContent(String illegalContent) {
		this.illegalContent = illegalContent;
	}
	public String getIllegalTime() {
		return illegalTime;
	}
	public void setIllegalTime(String illegalTime) {
		this.illegalTime = illegalTime;
	}
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}
	public String getReviewComment() {
		return reviewComment;
	}
	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}
	public String getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}
	public int getIsPost() {
		return isPost;
	}
	public void setIsPost(int isPost) {
		this.isPost = isPost;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


}
