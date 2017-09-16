package com.huzhouport.inspection.model;

import com.huzhouport.common.model.QueryCondition;

public class Inspection implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int inspectionId;
	private int inspectionUser;
	private int inspectionKind;
	private int inspectionObjectKind;
	private String inspectionObject;
	private String inspectionContent;
	private String inspectionTime;
	private int inspectionLocation;
	private int insCategoriese;//1:港政2：运政3：航政4：海事
	private int reviewWether;//0待审核，1审核
	private int reviewResult;//1通过2未通过
	private int reviewUser;
	private String reviewComment;
	private String reviewTime;
	private QueryCondition queryCondition=new QueryCondition();
	public int getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(int inspectionId) {
		this.inspectionId = inspectionId;
	}
	public int getInspectionUser() {
		return inspectionUser;
	}
	public void setInspectionUser(int inspectionUser) {
		this.inspectionUser = inspectionUser;
	}
	public int getInspectionKind() {
		return inspectionKind;
	}
	public void setInspectionKind(int inspectionKind) {
		this.inspectionKind = inspectionKind;
	}
	public int getInspectionObjectKind() {
		return inspectionObjectKind;
	}
	public void setInspectionObjectKind(int inspectionObjectKind) {
		this.inspectionObjectKind = inspectionObjectKind;
	}
	public String getInspectionObject() {
		return inspectionObject;
	}
	public void setInspectionObject(String inspectionObject) {
		this.inspectionObject = inspectionObject;
	}
	public String getInspectionContent() {
		return inspectionContent;
	}
	public void setInspectionContent(String inspectionContent) {
		this.inspectionContent = inspectionContent;
	}
	public String getInspectionTime() {
		return inspectionTime;
	}
	public void setInspectionTime(String inspectionTime) {
		this.inspectionTime = inspectionTime;
	}
	public int getInspectionLocation() {
		return inspectionLocation;
	}
	public void setInspectionLocation(int inspectionLocation) {
		this.inspectionLocation = inspectionLocation;
	}
	
	public int getInsCategoriese() {
		return insCategoriese;
	}
	public void setInsCategoriese(int insCategoriese) {
		this.insCategoriese = insCategoriese;
	}
	public int getReviewWether() {
		return reviewWether;
	}
	public void setReviewWether(int reviewWether) {
		this.reviewWether = reviewWether;
	}
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}
	public int getReviewUser() {
		return reviewUser;
	}
	public void setReviewUser(int reviewUser) {
		this.reviewUser = reviewUser;
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
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	
	
	

}
