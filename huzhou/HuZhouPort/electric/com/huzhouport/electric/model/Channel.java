package com.huzhouport.electric.model;

import com.huzhouport.common.model.QueryCondition;

public class Channel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int channelId; // 航道ID
	private String riversCode; // 河流代码
	private String riverName; // 河流名称
	private String channelCoding; // 航道编码
	private String channelName; // 航道名称
	private String reportUnit; // 上报单位
	private String administrativeArea; // 航段所在行政区划
	private String startingName; // 起点名称
	private String startingMileage; // 航段起点距航道起点里程
	private String startingPointmain; // 起点是否为主控点
	private String startingPointoff; // 起点是否为分界点
	private String startingPointtype; // 起点分界点类别
	private String endingName; // 终点名称
	private String endingMileage; // 航段终点距航道起点里程
	private String endingPointmain; // 终点是否为主控点
	private String endingPointoff; // 终点是否为分界点
	private String endingPointotype; // 终点分界点类别
	private String whetherSegment; // 是否为界河航段
	private String segmentCategories; // 分界航段类别
	private String countriesName; // 相邻国家名称
	private String adjacentAdministrative;// 相邻行政区域
	private String organizationName; // 相邻航道管理机构名称
	private String managementAdministrative;// 相邻航道管理机构所在行政区域
	private String organizationNamed; // 相邻航道管理机构名称1
	private String managementAdministratived;// 相邻航道管理机构所在行政区域1
	private String organizationNamef; // 相邻航道管理机构名称2
	private String managementAdministrativef;// 相邻航道管理机构所在行政区域2
	private String auxiliarySegment; // 是否有分叉辅航段
	private String bottleneckSection; // 是否有瓶颈区段
	private String majorShoals; // 是否有主要浅滩
	private String repeatSegment; // 是否为重复航段
	private String segmentmileage; // 航段里程
	private String channelDepth; // 航道水深
	private String channelWidth; // 航道宽度
	private String channelRadius; // 航道最小弯曲半径
	private String segmentTechnology; // 航段现状技术等级
	private String segmentClassification;// 航段定级技术等级
	private String segmentAttributes; // 航段属性
	private String channelOrganization;// 所在航道管理机构名称
	private String institutionsAdministrativearea;// 管理机构所在行政区域
	private String segmentNavigation; // 航段是否通航
	private String waterAssurance; // 最低通航水位保证率
	private String channelSpecial; // 航道是否为专用航道
	private String channelSeasonal; // 航道是否为季节性航道
	private String segmentType; // 航段维护类别
	private String categoryCloth; // 航标配布类别
	private String segmentVessel; // 航段海船代表船型载重吨
	private String coordinates; // 坐标
	private String coding; // 编码
	private String changeReason; // 变更原因
	private String picture; // 图片
	private String scope; // 范围
	
	private QueryCondition queryCondition = new QueryCondition();
	
	
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getRiversCode() {
		return riversCode;
	}
	public void setRiversCode(String riversCode) {
		this.riversCode = riversCode;
	}
	public String getRiverName() {
		return riverName;
	}
	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}
	public String getChannelCoding() {
		return channelCoding;
	}
	public void setChannelCoding(String channelCoding) {
		this.channelCoding = channelCoding;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getReportUnit() {
		return reportUnit;
	}
	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
	}
	public String getAdministrativeArea() {
		return administrativeArea;
	}
	public void setAdministrativeArea(String administrativeArea) {
		this.administrativeArea = administrativeArea;
	}
	public String getStartingName() {
		return startingName;
	}
	public void setStartingName(String startingName) {
		this.startingName = startingName;
	}
	public String getStartingMileage() {
		return startingMileage;
	}
	public void setStartingMileage(String startingMileage) {
		this.startingMileage = startingMileage;
	}
	public String getStartingPointmain() {
		return startingPointmain;
	}
	public void setStartingPointmain(String startingPointmain) {
		this.startingPointmain = startingPointmain;
	}
	public String getStartingPointoff() {
		return startingPointoff;
	}
	public void setStartingPointoff(String startingPointoff) {
		this.startingPointoff = startingPointoff;
	}
	public String getStartingPointtype() {
		return startingPointtype;
	}
	public void setStartingPointtype(String startingPointtype) {
		this.startingPointtype = startingPointtype;
	}
	public String getEndingName() {
		return endingName;
	}
	public void setEndingName(String endingName) {
		this.endingName = endingName;
	}
	public String getEndingMileage() {
		return endingMileage;
	}
	public void setEndingMileage(String endingMileage) {
		this.endingMileage = endingMileage;
	}
	public String getEndingPointmain() {
		return endingPointmain;
	}
	public void setEndingPointmain(String endingPointmain) {
		this.endingPointmain = endingPointmain;
	}
	public String getEndingPointoff() {
		return endingPointoff;
	}
	public void setEndingPointoff(String endingPointoff) {
		this.endingPointoff = endingPointoff;
	}
	public String getEndingPointotype() {
		return endingPointotype;
	}
	public void setEndingPointotype(String endingPointotype) {
		this.endingPointotype = endingPointotype;
	}
	public String getWhetherSegment() {
		return whetherSegment;
	}
	public void setWhetherSegment(String whetherSegment) {
		this.whetherSegment = whetherSegment;
	}
	public String getSegmentCategories() {
		return segmentCategories;
	}
	public void setSegmentCategories(String segmentCategories) {
		this.segmentCategories = segmentCategories;
	}
	public String getCountriesName() {
		return countriesName;
	}
	public void setCountriesName(String countriesName) {
		this.countriesName = countriesName;
	}
	public String getAdjacentAdministrative() {
		return adjacentAdministrative;
	}
	public void setAdjacentAdministrative(String adjacentAdministrative) {
		this.adjacentAdministrative = adjacentAdministrative;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getManagementAdministrative() {
		return managementAdministrative;
	}
	public void setManagementAdministrative(String managementAdministrative) {
		this.managementAdministrative = managementAdministrative;
	}
	public String getOrganizationNamed() {
		return organizationNamed;
	}
	public void setOrganizationNamed(String organizationNamed) {
		this.organizationNamed = organizationNamed;
	}
	public String getManagementAdministratived() {
		return managementAdministratived;
	}
	public void setManagementAdministratived(String managementAdministratived) {
		this.managementAdministratived = managementAdministratived;
	}
	public String getOrganizationNamef() {
		return organizationNamef;
	}
	public void setOrganizationNamef(String organizationNamef) {
		this.organizationNamef = organizationNamef;
	}
	public String getManagementAdministrativef() {
		return managementAdministrativef;
	}
	public void setManagementAdministrativef(String managementAdministrativef) {
		this.managementAdministrativef = managementAdministrativef;
	}
	public String getAuxiliarySegment() {
		return auxiliarySegment;
	}
	public void setAuxiliarySegment(String auxiliarySegment) {
		this.auxiliarySegment = auxiliarySegment;
	}
	public String getBottleneckSection() {
		return bottleneckSection;
	}
	public void setBottleneckSection(String bottleneckSection) {
		this.bottleneckSection = bottleneckSection;
	}
	public String getMajorShoals() {
		return majorShoals;
	}
	public void setMajorShoals(String majorShoals) {
		this.majorShoals = majorShoals;
	}
	public String getRepeatSegment() {
		return repeatSegment;
	}
	public void setRepeatSegment(String repeatSegment) {
		this.repeatSegment = repeatSegment;
	}
	public String getSegmentmileage() {
		return segmentmileage;
	}
	public void setSegmentmileage(String segmentmileage) {
		this.segmentmileage = segmentmileage;
	}
	public String getChannelDepth() {
		return channelDepth;
	}
	public void setChannelDepth(String channelDepth) {
		this.channelDepth = channelDepth;
	}
	public String getChannelWidth() {
		return channelWidth;
	}
	public void setChannelWidth(String channelWidth) {
		this.channelWidth = channelWidth;
	}
	public String getChannelRadius() {
		return channelRadius;
	}
	public void setChannelRadius(String channelRadius) {
		this.channelRadius = channelRadius;
	}
	public String getSegmentTechnology() {
		return segmentTechnology;
	}
	public void setSegmentTechnology(String segmentTechnology) {
		this.segmentTechnology = segmentTechnology;
	}
	public String getSegmentClassification() {
		return segmentClassification;
	}
	public void setSegmentClassification(String segmentClassification) {
		this.segmentClassification = segmentClassification;
	}
	public String getSegmentAttributes() {
		return segmentAttributes;
	}
	public void setSegmentAttributes(String segmentAttributes) {
		this.segmentAttributes = segmentAttributes;
	}
	public String getChannelOrganization() {
		return channelOrganization;
	}
	public void setChannelOrganization(String channelOrganization) {
		this.channelOrganization = channelOrganization;
	}
	public String getInstitutionsAdministrativearea() {
		return institutionsAdministrativearea;
	}
	public void setInstitutionsAdministrativearea(
			String institutionsAdministrativearea) {
		this.institutionsAdministrativearea = institutionsAdministrativearea;
	}
	public String getSegmentNavigation() {
		return segmentNavigation;
	}
	public void setSegmentNavigation(String segmentNavigation) {
		this.segmentNavigation = segmentNavigation;
	}
	public String getWaterAssurance() {
		return waterAssurance;
	}
	public void setWaterAssurance(String waterAssurance) {
		this.waterAssurance = waterAssurance;
	}
	public String getChannelSpecial() {
		return channelSpecial;
	}
	public void setChannelSpecial(String channelSpecial) {
		this.channelSpecial = channelSpecial;
	}
	public String getChannelSeasonal() {
		return channelSeasonal;
	}
	public void setChannelSeasonal(String channelSeasonal) {
		this.channelSeasonal = channelSeasonal;
	}
	public String getSegmentType() {
		return segmentType;
	}
	public void setSegmentType(String segmentType) {
		this.segmentType = segmentType;
	}
	public String getCategoryCloth() {
		return categoryCloth;
	}
	public void setCategoryCloth(String categoryCloth) {
		this.categoryCloth = categoryCloth;
	}
	public String getSegmentVessel() {
		return segmentVessel;
	}
	public void setSegmentVessel(String segmentVessel) {
		this.segmentVessel = segmentVessel;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	public String getCoding() {
		return coding;
	}
	public void setCoding(String coding) {
		this.coding = coding;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

}
