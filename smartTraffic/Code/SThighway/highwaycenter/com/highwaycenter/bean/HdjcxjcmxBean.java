package com.highwaycenter.bean;



public class HdjcxjcmxBean implements java.io.Serializable {  //涵洞检查明细

	private static final long serialVersionUID = 4035515480781819362L;
	private String id;
	private String checkId;
	private String componentId;
	private String componentCode;
	private String componentName;
	private String description;
	private String suggestion;
	private String memo;
	private String judgeType;
	
	public HdjcxjcmxBean(){
		
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getJudgeType() {
		return judgeType;
	}
	public void setJudgeType(String judgeType) {
		this.judgeType = judgeType;
	}
	
	
}
