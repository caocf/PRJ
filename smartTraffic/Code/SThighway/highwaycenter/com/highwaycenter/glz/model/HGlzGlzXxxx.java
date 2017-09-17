package com.highwaycenter.glz.model;

import java.math.BigDecimal;

public class HGlzGlzXxxx  implements java.io.Serializable {

	private static final long serialVersionUID = 403398885690191906L;
	
	
	// Fields

	private String id;
	private String stationId;
	private Integer year;
	private BigDecimal maintainTotal;
	private BigDecimal bridgeCount;
	private BigDecimal bridgeLong;
	private BigDecimal tunnelCount;
	private BigDecimal tunnelLong;
	private BigDecimal greenLong;
	private BigDecimal greenPercent;
	private BigDecimal countryRoad;
	private BigDecimal provinceRoad;
	private BigDecimal cityRoad;
	private BigDecimal firstLevelRoad;
	private BigDecimal oneLevelRoad;
	private BigDecimal twoLevelRoad;
	private BigDecimal threeLevelRoad;
	private BigDecimal fourLevelRoad;
	private BigDecimal lastLevelRoad;
	private BigDecimal asphaltRoad;
	private BigDecimal cementRoad;
	private BigDecimal sandStoneRoad;
	private BigDecimal mainGoodIndex;
	private BigDecimal mainGoodRate;
	private BigDecimal subGoodIndex;
	private BigDecimal subGoodRate;
	private BigDecimal machineTotal;
	private BigDecimal machineMoney;
	private BigDecimal tractorCar;
	private BigDecimal rollerCar;
	private BigDecimal smallTruckCar;
	private BigDecimal asphaltCar;
	private BigDecimal loadCar;
	private BigDecimal waterCar;
	private BigDecimal sweepCar;
	private BigDecimal weedCar;
	private BigDecimal clearSewCar;
	private BigDecimal mixerCar;
	private BigDecimal fillSewCar;
	private BigDecimal inspectCar;
	private BigDecimal totalWorker;
	private BigDecimal fixedMoney;
	private BigDecimal fixedWorker;
	private BigDecimal tempWorker;
	private BigDecimal manWorker;
	private BigDecimal womanWorker;
	private BigDecimal thirtyFiveDown;
	private BigDecimal thirtyFiveToFortyFive;
	private BigDecimal fortyFiveUp;
	private BigDecimal college;
	private BigDecimal highSchool;
	private BigDecimal middleSchool;
	private BigDecimal partyCount;
	private BigDecimal memberCount;
	private BigDecimal allYearMoney;
	private BigDecimal avgMoneyKm;
	private String remark;
	private String blueprintFileId;
	private BigDecimal greenArea;
	private BigDecimal workArea;
	private BigDecimal liveArea;
	private BigDecimal totalArea;
	private BigDecimal issuedTarget;
	private BigDecimal issuedPercent;
	private BigDecimal fightTarget;
	private BigDecimal fightPercent;
	private BigDecimal provinceTarget;
	private BigDecimal countryTarget;
	private BigDecimal firstSeasonTarget;
	private BigDecimal secondSeasonTarget;
	private BigDecimal thirdSeasonTarget;
	private BigDecimal fourthSeasonTarget;

	// Constructors

	/** default constructor */
	public HGlzGlzXxxx() {
	}

	/** minimal constructor */
	public HGlzGlzXxxx(String id) {
		this.id = id;
	}

	/** full constructor */
	public HGlzGlzXxxx(String id, String stationId, Integer year,
			BigDecimal maintainTotal, BigDecimal bridgeCount, BigDecimal bridgeLong,
			BigDecimal tunnelCount, BigDecimal tunnelLong, BigDecimal greenLong,
			BigDecimal greenPercent, BigDecimal countryRoad, BigDecimal provinceRoad,
			BigDecimal cityRoad, BigDecimal firstLevelRoad, BigDecimal oneLevelRoad,
			BigDecimal twoLevelRoad, BigDecimal threeLevelRoad, BigDecimal fourLevelRoad,
			BigDecimal lastLevelRoad, BigDecimal asphaltRoad, BigDecimal cementRoad,
			BigDecimal sandStoneRoad, BigDecimal mainGoodIndex, BigDecimal mainGoodRate,
			BigDecimal subGoodIndex, BigDecimal subGoodRate, BigDecimal machineTotal,
			BigDecimal machineMoney, BigDecimal tractorCar, BigDecimal rollerCar,
			BigDecimal smallTruckCar, BigDecimal asphaltCar, BigDecimal loadCar,
			BigDecimal waterCar, BigDecimal sweepCar, BigDecimal weedCar,
			BigDecimal clearSewCar, BigDecimal mixerCar, BigDecimal fillSewCar,
			BigDecimal inspectCar, BigDecimal totalWorker, BigDecimal fixedMoney,
			BigDecimal fixedWorker, BigDecimal tempWorker, BigDecimal manWorker,
			BigDecimal womanWorker, BigDecimal thirtyFiveDown,
			BigDecimal thirtyFiveToFortyFive, BigDecimal fortyFiveUp, BigDecimal college,
			BigDecimal highSchool, BigDecimal middleSchool, BigDecimal partyCount,
			BigDecimal memberCount, BigDecimal allYearMoney, BigDecimal avgMoneyKm,
			String remark, String blueprintFileId, BigDecimal greenArea,
			BigDecimal workArea, BigDecimal liveArea, BigDecimal totalArea,
			BigDecimal issuedTarget, BigDecimal issuedPercent, BigDecimal fightTarget,
			BigDecimal fightPercent, BigDecimal provinceTarget, BigDecimal countryTarget,
			BigDecimal firstSeasonTarget, BigDecimal secondSeasonTarget,
			BigDecimal thirdSeasonTarget, BigDecimal fourthSeasonTarget) {
		this.id = id;
		this.stationId = stationId;
		this.year = year;
		this.maintainTotal = maintainTotal;
		this.bridgeCount = bridgeCount;
		this.bridgeLong = bridgeLong;
		this.tunnelCount = tunnelCount;
		this.tunnelLong = tunnelLong;
		this.greenLong = greenLong;
		this.greenPercent = greenPercent;
		this.countryRoad = countryRoad;
		this.provinceRoad = provinceRoad;
		this.cityRoad = cityRoad;
		this.firstLevelRoad = firstLevelRoad;
		this.oneLevelRoad = oneLevelRoad;
		this.twoLevelRoad = twoLevelRoad;
		this.threeLevelRoad = threeLevelRoad;
		this.fourLevelRoad = fourLevelRoad;
		this.lastLevelRoad = lastLevelRoad;
		this.asphaltRoad = asphaltRoad;
		this.cementRoad = cementRoad;
		this.sandStoneRoad = sandStoneRoad;
		this.mainGoodIndex = mainGoodIndex;
		this.mainGoodRate = mainGoodRate;
		this.subGoodIndex = subGoodIndex;
		this.subGoodRate = subGoodRate;
		this.machineTotal = machineTotal;
		this.machineMoney = machineMoney;
		this.tractorCar = tractorCar;
		this.rollerCar = rollerCar;
		this.smallTruckCar = smallTruckCar;
		this.asphaltCar = asphaltCar;
		this.loadCar = loadCar;
		this.waterCar = waterCar;
		this.sweepCar = sweepCar;
		this.weedCar = weedCar;
		this.clearSewCar = clearSewCar;
		this.mixerCar = mixerCar;
		this.fillSewCar = fillSewCar;
		this.inspectCar = inspectCar;
		this.totalWorker = totalWorker;
		this.fixedMoney = fixedMoney;
		this.fixedWorker = fixedWorker;
		this.tempWorker = tempWorker;
		this.manWorker = manWorker;
		this.womanWorker = womanWorker;
		this.thirtyFiveDown = thirtyFiveDown;
		this.thirtyFiveToFortyFive = thirtyFiveToFortyFive;
		this.fortyFiveUp = fortyFiveUp;
		this.college = college;
		this.highSchool = highSchool;
		this.middleSchool = middleSchool;
		this.partyCount = partyCount;
		this.memberCount = memberCount;
		this.allYearMoney = allYearMoney;
		this.avgMoneyKm = avgMoneyKm;
		this.remark = remark;
		this.blueprintFileId = blueprintFileId;
		this.greenArea = greenArea;
		this.workArea = workArea;
		this.liveArea = liveArea;
		this.totalArea = totalArea;
		this.issuedTarget = issuedTarget;
		this.issuedPercent = issuedPercent;
		this.fightTarget = fightTarget;
		this.fightPercent = fightPercent;
		this.provinceTarget = provinceTarget;
		this.countryTarget = countryTarget;
		this.firstSeasonTarget = firstSeasonTarget;
		this.secondSeasonTarget = secondSeasonTarget;
		this.thirdSeasonTarget = thirdSeasonTarget;
		this.fourthSeasonTarget = fourthSeasonTarget;
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

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getMaintainTotal() {
		return this.maintainTotal;
	}

	public void setMaintainTotal(BigDecimal maintainTotal) {
		this.maintainTotal = maintainTotal;
	}

	public BigDecimal getBridgeCount() {
		return this.bridgeCount;
	}

	public void setBridgeCount(BigDecimal bridgeCount) {
		this.bridgeCount = bridgeCount;
	}

	public BigDecimal getBridgeLong() {
		return this.bridgeLong;
	}

	public void setBridgeLong(BigDecimal bridgeLong) {
		this.bridgeLong = bridgeLong;
	}

	public BigDecimal getTunnelCount() {
		return this.tunnelCount;
	}

	public void setTunnelCount(BigDecimal tunnelCount) {
		this.tunnelCount = tunnelCount;
	}

	public BigDecimal getTunnelLong() {
		return this.tunnelLong;
	}

	public void setTunnelLong(BigDecimal tunnelLong) {
		this.tunnelLong = tunnelLong;
	}

	public BigDecimal getGreenLong() {
		return this.greenLong;
	}

	public void setGreenLong(BigDecimal greenLong) {
		this.greenLong = greenLong;
	}

	public BigDecimal getGreenPercent() {
		return this.greenPercent;
	}

	public void setGreenPercent(BigDecimal greenPercent) {
		this.greenPercent = greenPercent;
	}

	public BigDecimal getCountryRoad() {
		return this.countryRoad;
	}

	public void setCountryRoad(BigDecimal countryRoad) {
		this.countryRoad = countryRoad;
	}

	public BigDecimal getProvinceRoad() {
		return this.provinceRoad;
	}

	public void setProvinceRoad(BigDecimal provinceRoad) {
		this.provinceRoad = provinceRoad;
	}

	public BigDecimal getCityRoad() {
		return this.cityRoad;
	}

	public void setCityRoad(BigDecimal cityRoad) {
		this.cityRoad = cityRoad;
	}

	public BigDecimal getFirstLevelRoad() {
		return this.firstLevelRoad;
	}

	public void setFirstLevelRoad(BigDecimal firstLevelRoad) {
		this.firstLevelRoad = firstLevelRoad;
	}

	public BigDecimal getOneLevelRoad() {
		return this.oneLevelRoad;
	}

	public void setOneLevelRoad(BigDecimal oneLevelRoad) {
		this.oneLevelRoad = oneLevelRoad;
	}

	public BigDecimal getTwoLevelRoad() {
		return this.twoLevelRoad;
	}

	public void setTwoLevelRoad(BigDecimal twoLevelRoad) {
		this.twoLevelRoad = twoLevelRoad;
	}

	public BigDecimal getThreeLevelRoad() {
		return this.threeLevelRoad;
	}

	public void setThreeLevelRoad(BigDecimal threeLevelRoad) {
		this.threeLevelRoad = threeLevelRoad;
	}

	public BigDecimal getFourLevelRoad() {
		return this.fourLevelRoad;
	}

	public void setFourLevelRoad(BigDecimal fourLevelRoad) {
		this.fourLevelRoad = fourLevelRoad;
	}

	public BigDecimal getLastLevelRoad() {
		return this.lastLevelRoad;
	}

	public void setLastLevelRoad(BigDecimal lastLevelRoad) {
		this.lastLevelRoad = lastLevelRoad;
	}

	public BigDecimal getAsphaltRoad() {
		return this.asphaltRoad;
	}

	public void setAsphaltRoad(BigDecimal asphaltRoad) {
		this.asphaltRoad = asphaltRoad;
	}

	public BigDecimal getCementRoad() {
		return this.cementRoad;
	}

	public void setCementRoad(BigDecimal cementRoad) {
		this.cementRoad = cementRoad;
	}

	public BigDecimal getSandStoneRoad() {
		return this.sandStoneRoad;
	}

	public void setSandStoneRoad(BigDecimal sandStoneRoad) {
		this.sandStoneRoad = sandStoneRoad;
	}

	public BigDecimal getMainGoodIndex() {
		return this.mainGoodIndex;
	}

	public void setMainGoodIndex(BigDecimal mainGoodIndex) {
		this.mainGoodIndex = mainGoodIndex;
	}

	public BigDecimal getMainGoodRate() {
		return this.mainGoodRate;
	}

	public void setMainGoodRate(BigDecimal mainGoodRate) {
		this.mainGoodRate = mainGoodRate;
	}

	public BigDecimal getSubGoodIndex() {
		return this.subGoodIndex;
	}

	public void setSubGoodIndex(BigDecimal subGoodIndex) {
		this.subGoodIndex = subGoodIndex;
	}

	public BigDecimal getSubGoodRate() {
		return this.subGoodRate;
	}

	public void setSubGoodRate(BigDecimal subGoodRate) {
		this.subGoodRate = subGoodRate;
	}

	public BigDecimal getMachineTotal() {
		return this.machineTotal;
	}

	public void setMachineTotal(BigDecimal machineTotal) {
		this.machineTotal = machineTotal;
	}

	public BigDecimal getMachineMoney() {
		return this.machineMoney;
	}

	public void setMachineMoney(BigDecimal machineMoney) {
		this.machineMoney = machineMoney;
	}

	public BigDecimal getTractorCar() {
		return this.tractorCar;
	}

	public void setTractorCar(BigDecimal tractorCar) {
		this.tractorCar = tractorCar;
	}

	public BigDecimal getRollerCar() {
		return this.rollerCar;
	}

	public void setRollerCar(BigDecimal rollerCar) {
		this.rollerCar = rollerCar;
	}

	public BigDecimal getSmallTruckCar() {
		return this.smallTruckCar;
	}

	public void setSmallTruckCar(BigDecimal smallTruckCar) {
		this.smallTruckCar = smallTruckCar;
	}

	public BigDecimal getAsphaltCar() {
		return this.asphaltCar;
	}

	public void setAsphaltCar(BigDecimal asphaltCar) {
		this.asphaltCar = asphaltCar;
	}

	public BigDecimal getLoadCar() {
		return this.loadCar;
	}

	public void setLoadCar(BigDecimal loadCar) {
		this.loadCar = loadCar;
	}

	public BigDecimal getWaterCar() {
		return this.waterCar;
	}

	public void setWaterCar(BigDecimal waterCar) {
		this.waterCar = waterCar;
	}

	public BigDecimal getSweepCar() {
		return this.sweepCar;
	}

	public void setSweepCar(BigDecimal sweepCar) {
		this.sweepCar = sweepCar;
	}

	public BigDecimal getWeedCar() {
		return this.weedCar;
	}

	public void setWeedCar(BigDecimal weedCar) {
		this.weedCar = weedCar;
	}

	public BigDecimal getClearSewCar() {
		return this.clearSewCar;
	}

	public void setClearSewCar(BigDecimal clearSewCar) {
		this.clearSewCar = clearSewCar;
	}

	public BigDecimal getMixerCar() {
		return this.mixerCar;
	}

	public void setMixerCar(BigDecimal mixerCar) {
		this.mixerCar = mixerCar;
	}

	public BigDecimal getFillSewCar() {
		return this.fillSewCar;
	}

	public void setFillSewCar(BigDecimal fillSewCar) {
		this.fillSewCar = fillSewCar;
	}

	public BigDecimal getInspectCar() {
		return this.inspectCar;
	}

	public void setInspectCar(BigDecimal inspectCar) {
		this.inspectCar = inspectCar;
	}

	public BigDecimal getTotalWorker() {
		return this.totalWorker;
	}

	public void setTotalWorker(BigDecimal totalWorker) {
		this.totalWorker = totalWorker;
	}

	public BigDecimal getFixedMoney() {
		return this.fixedMoney;
	}

	public void setFixedMoney(BigDecimal fixedMoney) {
		this.fixedMoney = fixedMoney;
	}

	public BigDecimal getFixedWorker() {
		return this.fixedWorker;
	}

	public void setFixedWorker(BigDecimal fixedWorker) {
		this.fixedWorker = fixedWorker;
	}

	public BigDecimal getTempWorker() {
		return this.tempWorker;
	}

	public void setTempWorker(BigDecimal tempWorker) {
		this.tempWorker = tempWorker;
	}

	public BigDecimal getManWorker() {
		return this.manWorker;
	}

	public void setManWorker(BigDecimal manWorker) {
		this.manWorker = manWorker;
	}

	public BigDecimal getWomanWorker() {
		return this.womanWorker;
	}

	public void setWomanWorker(BigDecimal womanWorker) {
		this.womanWorker = womanWorker;
	}

	public BigDecimal getThirtyFiveDown() {
		return this.thirtyFiveDown;
	}

	public void setThirtyFiveDown(BigDecimal thirtyFiveDown) {
		this.thirtyFiveDown = thirtyFiveDown;
	}

	public BigDecimal getThirtyFiveToFortyFive() {
		return this.thirtyFiveToFortyFive;
	}

	public void setThirtyFiveToFortyFive(BigDecimal thirtyFiveToFortyFive) {
		this.thirtyFiveToFortyFive = thirtyFiveToFortyFive;
	}

	public BigDecimal getFortyFiveUp() {
		return this.fortyFiveUp;
	}

	public void setFortyFiveUp(BigDecimal fortyFiveUp) {
		this.fortyFiveUp = fortyFiveUp;
	}

	public BigDecimal getCollege() {
		return this.college;
	}

	public void setCollege(BigDecimal college) {
		this.college = college;
	}

	public BigDecimal getHighSchool() {
		return this.highSchool;
	}

	public void setHighSchool(BigDecimal highSchool) {
		this.highSchool = highSchool;
	}

	public BigDecimal getMiddleSchool() {
		return this.middleSchool;
	}

	public void setMiddleSchool(BigDecimal middleSchool) {
		this.middleSchool = middleSchool;
	}

	public BigDecimal getPartyCount() {
		return this.partyCount;
	}

	public void setPartyCount(BigDecimal partyCount) {
		this.partyCount = partyCount;
	}

	public BigDecimal getMemberCount() {
		return this.memberCount;
	}

	public void setMemberCount(BigDecimal memberCount) {
		this.memberCount = memberCount;
	}

	public BigDecimal getAllYearMoney() {
		return this.allYearMoney;
	}

	public void setAllYearMoney(BigDecimal allYearMoney) {
		this.allYearMoney = allYearMoney;
	}

	public BigDecimal getAvgMoneyKm() {
		return this.avgMoneyKm;
	}

	public void setAvgMoneyKm(BigDecimal avgMoneyKm) {
		this.avgMoneyKm = avgMoneyKm;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBlueprintFileId() {
		return this.blueprintFileId;
	}

	public void setBlueprintFileId(String blueprintFileId) {
		this.blueprintFileId = blueprintFileId;
	}

	public BigDecimal getGreenArea() {
		return this.greenArea;
	}

	public void setGreenArea(BigDecimal greenArea) {
		this.greenArea = greenArea;
	}

	public BigDecimal getWorkArea() {
		return this.workArea;
	}

	public void setWorkArea(BigDecimal workArea) {
		this.workArea = workArea;
	}

	public BigDecimal getLiveArea() {
		return this.liveArea;
	}

	public void setLiveArea(BigDecimal liveArea) {
		this.liveArea = liveArea;
	}

	public BigDecimal getTotalArea() {
		return this.totalArea;
	}

	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
	}

	public BigDecimal getIssuedTarget() {
		return this.issuedTarget;
	}

	public void setIssuedTarget(BigDecimal issuedTarget) {
		this.issuedTarget = issuedTarget;
	}

	public BigDecimal getIssuedPercent() {
		return this.issuedPercent;
	}

	public void setIssuedPercent(BigDecimal issuedPercent) {
		this.issuedPercent = issuedPercent;
	}

	public BigDecimal getFightTarget() {
		return this.fightTarget;
	}

	public void setFightTarget(BigDecimal fightTarget) {
		this.fightTarget = fightTarget;
	}

	public BigDecimal getFightPercent() {
		return this.fightPercent;
	}

	public void setFightPercent(BigDecimal fightPercent) {
		this.fightPercent = fightPercent;
	}

	public BigDecimal getProvinceTarget() {
		return this.provinceTarget;
	}

	public void setProvinceTarget(BigDecimal provinceTarget) {
		this.provinceTarget = provinceTarget;
	}

	public BigDecimal getCountryTarget() {
		return this.countryTarget;
	}

	public void setCountryTarget(BigDecimal countryTarget) {
		this.countryTarget = countryTarget;
	}

	public BigDecimal getFirstSeasonTarget() {
		return this.firstSeasonTarget;
	}

	public void setFirstSeasonTarget(BigDecimal firstSeasonTarget) {
		this.firstSeasonTarget = firstSeasonTarget;
	}

	public BigDecimal getSecondSeasonTarget() {
		return this.secondSeasonTarget;
	}

	public void setSecondSeasonTarget(BigDecimal secondSeasonTarget) {
		this.secondSeasonTarget = secondSeasonTarget;
	}

	public BigDecimal getThirdSeasonTarget() {
		return this.thirdSeasonTarget;
	}

	public void setThirdSeasonTarget(BigDecimal thirdSeasonTarget) {
		this.thirdSeasonTarget = thirdSeasonTarget;
	}

	public BigDecimal getFourthSeasonTarget() {
		return this.fourthSeasonTarget;
	}

	public void setFourthSeasonTarget(BigDecimal fourthSeasonTarget) {
		this.fourthSeasonTarget = fourthSeasonTarget;
	}

}
