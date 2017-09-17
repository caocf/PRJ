package com.highwaycenter.tzxx.model;

import java.util.Date;

/**
 * HTzTzxxb entity. @author MyEclipse Persistence Tools
 */

public class HTzTzxxb implements java.io.Serializable {

	// Fields

	private String mainId;
	private String title;
	private String dataValue;
	private Date createDate;
	private Date updateDate;
	private Date publishDate;
	private String publishUsername;
	private String columnId;
	private String columnName;
	private String disorder;

	// Constructors

	/** default constructor */
	public HTzTzxxb() {
	}

	/** minimal constructor */
	public HTzTzxxb(String mainId) {
		this.mainId = mainId;
	}

	public HTzTzxxb(String mainId, String title,Date createDate, Date updateDate, Date publishDate,
			String publishUsername,String columnName) {
		this.mainId = mainId;
		this.title = title;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.publishDate = publishDate;
		this.publishUsername = publishUsername;
		this.columnName = columnName;
	}
	/** full constructor */
	public HTzTzxxb(String mainId, String title, String dataValue,
			Date createDate, Date updateDate, Date publishDate,
			String publishUsername, String columnId, String columnName,
			String disorder) {
		this.mainId = mainId;
		this.title = title;
		this.dataValue = dataValue;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.publishDate = publishDate;
		this.publishUsername = publishUsername;
		this.columnId = columnId;
		this.columnName = columnName;
		this.disorder = disorder;
	}

	// Property accessors

	public String getMainId() {
		return this.mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishUsername() {
		return this.publishUsername;
	}

	public void setPublishUsername(String publishUsername) {
		this.publishUsername = publishUsername;
	}

	public String getColumnId() {
		return this.columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDisorder() {
		return this.disorder;
	}

	public void setDisorder(String disorder) {
		this.disorder = disorder;
	}

}