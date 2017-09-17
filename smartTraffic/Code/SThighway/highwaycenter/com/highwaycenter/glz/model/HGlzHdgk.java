package com.highwaycenter.glz.model;

import java.sql.Timestamp;

/**
 * HGlzHdgk entity. @author MyEclipse Persistence Tools
 */

public class HGlzHdgk implements java.io.Serializable {

	private static final long serialVersionUID = -6534088719598485277L;
	private String id;
	private String code;
	private String stake;
	private Double stake2;
	private Integer type;
	private Double radius;
	private Double height;
	private Double culvertLong;
	private Double width;
	private Timestamp completionDate;
	private Integer hdjszk;
	private Integer ks;
	private Integer jkxs;
	private Integer ckxs;
	private String versionId;
	private Short removeMark;
	private Short latestMark;
	private Integer historyNo;

	// Constructors

	/** default constructor */
	public HGlzHdgk() {
	}
	
	public HGlzHdgk(String id, String code, String stake, Double stake2,
			Integer type,Double culvertLong) {
		this.id = id;
		this.code = code;
		this.stake = stake;
		this.stake2 = stake2;
		this.type = type;
		this.culvertLong = culvertLong;

	}
	

	/** minimal constructor */
	public HGlzHdgk(String id) {
		this.id = id;
	}

	/** full constructor */
	public HGlzHdgk(String id, String code, String stake, Double stake2,
			Integer type, Double radius, Double height, Double culvertLong,
			Double width, Timestamp completionDate, Integer hdjszk, Integer ks,
			Integer jkxs, Integer ckxs, String versionId, Short removeMark,
			Short latestMark, Integer historyNo) {
		this.id = id;
		this.code = code;
		this.stake = stake;
		this.stake2 = stake2;
		this.type = type;
		this.radius = radius;
		this.height = height;
		this.culvertLong = culvertLong;
		this.width = width;
		this.completionDate = completionDate;
		this.hdjszk = hdjszk;
		this.ks = ks;
		this.jkxs = jkxs;
		this.ckxs = ckxs;
		this.versionId = versionId;
		this.removeMark = removeMark;
		this.latestMark = latestMark;
		this.historyNo = historyNo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStake() {
		return this.stake;
	}

	public void setStake(String stake) {
		this.stake = stake;
	}

	public Double getStake2() {
		return this.stake2;
	}

	public void setStake2(Double stake2) {
		this.stake2 = stake2;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getRadius() {
		return this.radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public Double getHeight() {
		return this.height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getCulvertLong() {
		return this.culvertLong;
	}

	public void setCulvertLong(Double culvertLong) {
		this.culvertLong = culvertLong;
	}

	public Double getWidth() {
		return this.width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Timestamp getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

	public Integer getHdjszk() {
		return this.hdjszk;
	}

	public void setHdjszk(Integer hdjszk) {
		this.hdjszk = hdjszk;
	}

	public Integer getKs() {
		return this.ks;
	}

	public void setKs(Integer ks) {
		this.ks = ks;
	}

	public Integer getJkxs() {
		return this.jkxs;
	}

	public void setJkxs(Integer jkxs) {
		this.jkxs = jkxs;
	}

	public Integer getCkxs() {
		return this.ckxs;
	}

	public void setCkxs(Integer ckxs) {
		this.ckxs = ckxs;
	}

	public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public Short getRemoveMark() {
		return this.removeMark;
	}

	public void setRemoveMark(Short removeMark) {
		this.removeMark = removeMark;
	}

	public Short getLatestMark() {
		return this.latestMark;
	}

	public void setLatestMark(Short latestMark) {
		this.latestMark = latestMark;
	}

	public Integer getHistoryNo() {
		return this.historyNo;
	}

	public void setHistoryNo(Integer historyNo) {
		this.historyNo = historyNo;
	}

}