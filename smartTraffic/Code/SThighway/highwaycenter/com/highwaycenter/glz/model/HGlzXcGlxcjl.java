package com.highwaycenter.glz.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Transient;

import com.highwaycenter.bean.GlxcldBean;

/**
 * HGlzXcGlxcjl entity. @author MyEclipse Persistence Tools
 */

public class HGlzXcGlxcjl implements java.io.Serializable {

	private static final long serialVersionUID = -7766416071801483748L;
	private String id;
	private String stationId;
	private String sectionId;
	private Timestamp inspectDate;
	private String weather;
	private String roadName;
	private String stake;
	private String content;
	private String inspector;
	private String opinion;
	private String signature;
	private Timestamp signDate;
	private String handleResult;
	private String handlerSignature;
	private Timestamp handleDate;
	private String lsId;
	@Transient
	private String inspectDateString;
	@Transient
	private List<GlxcldBean> gyldlist;
	// Constructors

	/** default constructor */
	public HGlzXcGlxcjl() {
	}

	public HGlzXcGlxcjl(String id) {
		this.id = id;
	}
	/** minimal constructor */
	public HGlzXcGlxcjl(String id, String stationId,Timestamp inspectDate, String weather,String content, String inspector, String signature,
			String handlerSignature, Timestamp handleDate) {
		this.id = id;
		this.stationId = stationId;
		this.inspectDate = inspectDate;
		this.weather = weather;
		this.content = content;
		this.inspector = inspector;
		this.signature = signature;
		this.handlerSignature = handlerSignature;
		this.handleDate = handleDate;

	}

	/** full constructor */
	public HGlzXcGlxcjl(String id, String stationId, String sectionId,
			Timestamp inspectDate, String weather, String roadName,
			String stake, String content, String inspector, String opinion,
			String signature, Timestamp signDate, String handleResult,
			String handlerSignature, Timestamp handleDate, String lsId) {
		this.id = id;
		this.stationId = stationId;
		this.sectionId = sectionId;
		this.inspectDate = inspectDate;
		this.weather = weather;
		this.roadName = roadName;
		this.stake = stake;
		this.content = content;
		this.inspector = inspector;
		this.opinion = opinion;
		this.signature = signature;
		this.signDate = signDate;
		this.handleResult = handleResult;
		this.handlerSignature = handlerSignature;
		this.handleDate = handleDate;
		this.lsId = lsId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public Timestamp getInspectDate() {
		return this.inspectDate;
	}

	public void setInspectDate(Timestamp inspectDate) {
		this.inspectDate = inspectDate;
	}

	public String getWeather() {
		return this.weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getRoadName() {
		return this.roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getStake() {
		return this.stake;
	}

	public void setStake(String stake) {
		this.stake = stake;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInspector() {
		return this.inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Timestamp getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Timestamp signDate) {
		this.signDate = signDate;
	}

	public String getHandleResult() {
		return this.handleResult;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}

	public String getHandlerSignature() {
		return this.handlerSignature;
	}

	public void setHandlerSignature(String handlerSignature) {
		this.handlerSignature = handlerSignature;
	}

	public Timestamp getHandleDate() {
		return this.handleDate;
	}

	public void setHandleDate(Timestamp handleDate) {
		this.handleDate = handleDate;
	}

	public String getLsId() {
		return this.lsId;
	}

	public void setLsId(String lsId) {
		this.lsId = lsId;
	}

	public String getInspectDateString() {
		return inspectDateString;
	}

	public void setInspectDateString(String inspectDateString) {
		this.inspectDateString = inspectDateString;
	}

	public List<GlxcldBean> getGyldlist() {
		return gyldlist;
	}

	public void setGyldlist(List<GlxcldBean> gyldlist) {
		this.gyldlist = gyldlist;
	}

}