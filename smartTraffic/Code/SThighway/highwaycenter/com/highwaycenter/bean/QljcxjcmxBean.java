package com.highwaycenter.bean;

public class QljcxjcmxBean  implements java.io.Serializable {

	private static final long serialVersionUID = -7906561154148807849L;
	private String id;
	private String checkId;
	private String componentId;
	private String componentCode;
	private String componentName;   //部件名称
	private String type;
	private String area;
	private String opinion;
	private String judgeType;
	
	

	public QljcxjcmxBean() {
		super();
	}
	public QljcxjcmxBean(String id, String checkId, String componentId,
			String componentName, String type, String area, String opinion,
			String judgeType) {
		super();
		this.id = id;
		this.checkId = checkId;
		this.componentId = componentId;
		this.componentName = componentName;
		this.type = type;
		this.area = area;
		this.opinion = opinion;
		this.judgeType = judgeType;
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
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getJudgeType() {
		return judgeType;
	}
	public void setJudgeType(String judgeType) {
		this.judgeType = judgeType;
	}
	
	
	
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	
	
}
