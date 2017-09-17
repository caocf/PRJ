package com.highwaycenter.glz.model;

/**
 * HGlzXcHdjcxjcmx entity. @author MyEclipse Persistence Tools
 */

public class HGlzXcHdjcxjcmx implements java.io.Serializable {

	private static final long serialVersionUID = 7903785580997253399L;
	private String id;
	private String checkId;
	private String componentId;
	private String description;
	private String suggestion;
	private String memo;
	private String judgeType;

	// Constructors

	/** default constructor */
	public HGlzXcHdjcxjcmx() {
	}

	/** minimal constructor */
	public HGlzXcHdjcxjcmx(String id, String checkId, String componentId) {
		this.id = id;
		this.checkId = checkId;
		this.componentId = componentId;
	}

	/** full constructor */
	public HGlzXcHdjcxjcmx(String id, String checkId, String componentId,
			String description, String suggestion, String memo, String judgeType) {
		this.id = id;
		this.checkId = checkId;
		this.componentId = componentId;
		this.description = description;
		this.suggestion = suggestion;
		this.memo = memo;
		this.judgeType = judgeType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getComponentId() {
		return this.componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSuggestion() {
		return this.suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getJudgeType() {
		return this.judgeType;
	}

	public void setJudgeType(String judgeType) {
		this.judgeType = judgeType;
	}

}