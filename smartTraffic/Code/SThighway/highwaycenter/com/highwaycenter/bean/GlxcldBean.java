package com.highwaycenter.bean;

import com.highwaycenter.glz.model.HGlzXcGlxcjl;


public class GlxcldBean implements java.io.Serializable {

	private static final long serialVersionUID = 3631488481000612142L;
	private String id;
	private String roadRecordId;
	private String sectionId;
	private String sectionName;
	private String stake;
	
	public GlxcldBean(){
		
	}
	
	public GlxcldBean(String id,String sectionName, String stake) {
		super();
		this.id = id;
		this.sectionName = sectionName;
		this.stake = stake;
	}
	
	public GlxcldBean(String id, String roadRecordId, String sectionId,
			String sectionName, String stake) {
		super();
		this.id = id;
		this.roadRecordId = roadRecordId;
		this.sectionId = sectionId;
		this.sectionName = sectionName;
		this.stake = stake;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoadRecordId() {
		return roadRecordId;
	}
	public void setRoadRecordId(String roadRecordId) {
		this.roadRecordId = roadRecordId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getStake() {
		return stake;
	}
	public void setStake(String stake) {
		this.stake = stake;
	}


	

}
