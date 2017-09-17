package com.highwaycenter.glz.model;
import java.sql.Timestamp;

import javax.persistence.Transient;
//46update
public class HGlzDbYscqktjxx implements java.io.Serializable {

	private static final long serialVersionUID = -7043451814776946602L;
	private String id;
	private String stationId;
	private Timestamp workDate;
	private Double planFund;
	private Double actualFund;
	private Double planSandStone;
	private Double actualSandStone;
	private Double planAsphalt;
	private Double actualAsphalt;
	private Double qualityNorm;
	private Double actualQuality;
	private Double qualityPercent;
	private Double planAmount;
	private Double actualAmount;
	private String projectQuality;
	private Double dutyPercent;
	private Double producePercent;
	@Transient
    private String workdateString;
	@Transient
    private String stationName;
	
	// Constructors

	/** default constructor */
	public HGlzDbYscqktjxx() {
	}

	/** minimal constructor */
	public HGlzDbYscqktjxx(String id) {
		this.id = id;
	}

	/** full constructor */
	public HGlzDbYscqktjxx(String id, String stationId, Timestamp workDate,
			Double planFund, Double actualFund, Double planSandStone,
			Double actualSandStone, Double planAsphalt, Double actualAsphalt,
			Double qualityNorm, Double actualQuality, Double qualityPercent,
			Double planAmount, Double actualAmount, String projectQuality,
			Double dutyPercent, Double producePercent) {
		this.id = id;
		this.stationId = stationId;
		this.workDate = workDate;
		this.planFund = planFund;
		this.actualFund = actualFund;
		this.planSandStone = planSandStone;
		this.actualSandStone = actualSandStone;
		this.planAsphalt = planAsphalt;
		this.actualAsphalt = actualAsphalt;
		this.qualityNorm = qualityNorm;
		this.actualQuality = actualQuality;
		this.qualityPercent = qualityPercent;
		this.planAmount = planAmount;
		this.actualAmount = actualAmount;
		this.projectQuality = projectQuality;
		this.dutyPercent = dutyPercent;
		this.producePercent = producePercent;
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

	public Timestamp getWorkDate() {
		return this.workDate;
	}

	public void setWorkDate(Timestamp workDate) {
		this.workDate = workDate;
	}

	public Double getPlanFund() {
		return this.planFund;
	}

	public void setPlanFund(Double planFund) {
		this.planFund = planFund;
	}

	public Double getActualFund() {
		return this.actualFund;
	}

	public void setActualFund(Double actualFund) {
		this.actualFund = actualFund;
	}

	public Double getPlanSandStone() {
		return this.planSandStone;
	}

	public void setPlanSandStone(Double planSandStone) {
		this.planSandStone = planSandStone;
	}

	public Double getActualSandStone() {
		return this.actualSandStone;
	}

	public void setActualSandStone(Double actualSandStone) {
		this.actualSandStone = actualSandStone;
	}

	public Double getPlanAsphalt() {
		return this.planAsphalt;
	}

	public void setPlanAsphalt(Double planAsphalt) {
		this.planAsphalt = planAsphalt;
	}

	public Double getActualAsphalt() {
		return this.actualAsphalt;
	}

	public void setActualAsphalt(Double actualAsphalt) {
		this.actualAsphalt = actualAsphalt;
	}

	public Double getQualityNorm() {
		return this.qualityNorm;
	}

	public void setQualityNorm(Double qualityNorm) {
		this.qualityNorm = qualityNorm;
	}

	public Double getActualQuality() {
		return this.actualQuality;
	}

	public void setActualQuality(Double actualQuality) {
		this.actualQuality = actualQuality;
	}

	public Double getQualityPercent() {
		return this.qualityPercent;
	}

	public void setQualityPercent(Double qualityPercent) {
		this.qualityPercent = qualityPercent;
	}

	public Double getPlanAmount() {
		return this.planAmount;
	}

	public void setPlanAmount(Double planAmount) {
		this.planAmount = planAmount;
	}

	public Double getActualAmount() {
		return this.actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getProjectQuality() {
		return this.projectQuality;
	}

	public void setProjectQuality(String projectQuality) {
		this.projectQuality = projectQuality;
	}

	public Double getDutyPercent() {
		return this.dutyPercent;
	}

	public void setDutyPercent(Double dutyPercent) {
		this.dutyPercent = dutyPercent;
	}

	public Double getProducePercent() {
		return this.producePercent;
	}

	public void setProducePercent(Double producePercent) {
		this.producePercent = producePercent;
	}

	public String getWorkdateString() {
		return workdateString;
	}

	public void setWorkdateString(String workdateString) {
		this.workdateString = workdateString;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	
}