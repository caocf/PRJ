package com.highwaycenter.glz.model;

/**
 * HGlzXcGlxcld entity. @author MyEclipse Persistence Tools
 */

public class HGlzXcGlxcld implements java.io.Serializable {

	private static final long serialVersionUID = 8609461314523167124L;
	private String id;
	private String roadRecordId;
	private String sectionId;
	private String stake;

	// Constructors

	/** default constructor */
	public HGlzXcGlxcld() {
	}

	/** minimal constructor */
	public HGlzXcGlxcld(String id) {
		this.id = id;
	}

	/** full constructor */
	public HGlzXcGlxcld(String id, String roadRecordId, String sectionId,
			String stake) {
		this.id = id;
		this.roadRecordId = roadRecordId;
		this.sectionId = sectionId;
		this.stake = stake;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoadRecordId() {
		return this.roadRecordId;
	}

	public void setRoadRecordId(String roadRecordId) {
		this.roadRecordId = roadRecordId;
	}

	public String getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getStake() {
		return this.stake;
	}

	public void setStake(String stake) {
		this.stake = stake;
	}

}