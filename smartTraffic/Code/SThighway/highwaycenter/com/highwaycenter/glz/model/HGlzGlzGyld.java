package com.highwaycenter.glz.model;

public class HGlzGlzGyld implements java.io.Serializable {

	private static final long serialVersionUID = 1433195912631411684L;
	private String id;
	private String code;
	private String type;
	private String stationId;
	private String districtId;
	private String sectionName;
	private String startStake;
	private Double startStake2;
	private String startStakeName;
	private String endStake;
	private Double endStake2;
	private String endStakeName;
	private Integer historyNo;
	private Short latestMark;
	private Short removeMark;
	private String versionId;
	private String rid;
	private Double manageLength;
	private Double qytclc;
	private String gldj;
	private Double npjjdcjtl;
	private Double ljkd;
	private Double qjxzxbj;
	private Double zdcp;
	private Double lmkd;
	private Double lmgjsntlc;
	private Double lmgjlqtlc;
	private Double lmcgjlc;
	private Double lmzjlc;
	private Double lmdjlc;
	private Double klhlc;
	private Double ylhlc;

	// Constructors

	/** default constructor */
	public HGlzGlzGyld() {
	}

	/** minimal constructor */
	public HGlzGlzGyld(String id) {
		this.id = id;
	}

	/** full constructor */
	public HGlzGlzGyld(String id, String code, String type, String stationId,
			String districtId, String sectionName, String startStake,
			Double startStake2, String startStakeName, String endStake,
			Double endStake2, String endStakeName, Integer historyNo,
			Short latestMark, Short removeMark, String versionId, String rid,
			Double manageLength, Double qytclc, String gldj, Double npjjdcjtl,
			Double ljkd, Double qjxzxbj, Double zdcp, Double lmkd,
			Double lmgjsntlc, Double lmgjlqtlc, Double lmcgjlc, Double lmzjlc,
			Double lmdjlc, Double klhlc, Double ylhlc) {
		this.id = id;
		this.code = code;
		this.type = type;
		this.stationId = stationId;
		this.districtId = districtId;
		this.sectionName = sectionName;
		this.startStake = startStake;
		this.startStake2 = startStake2;
		this.startStakeName = startStakeName;
		this.endStake = endStake;
		this.endStake2 = endStake2;
		this.endStakeName = endStakeName;
		this.historyNo = historyNo;
		this.latestMark = latestMark;
		this.removeMark = removeMark;
		this.versionId = versionId;
		this.rid = rid;
		this.manageLength = manageLength;
		this.qytclc = qytclc;
		this.gldj = gldj;
		this.npjjdcjtl = npjjdcjtl;
		this.ljkd = ljkd;
		this.qjxzxbj = qjxzxbj;
		this.zdcp = zdcp;
		this.lmkd = lmkd;
		this.lmgjsntlc = lmgjsntlc;
		this.lmgjlqtlc = lmgjlqtlc;
		this.lmcgjlc = lmcgjlc;
		this.lmzjlc = lmzjlc;
		this.lmdjlc = lmdjlc;
		this.klhlc = klhlc;
		this.ylhlc = ylhlc;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getStartStake() {
		return this.startStake;
	}

	public void setStartStake(String startStake) {
		this.startStake = startStake;
	}

	public Double getStartStake2() {
		return this.startStake2;
	}

	public void setStartStake2(Double startStake2) {
		this.startStake2 = startStake2;
	}

	public String getStartStakeName() {
		return this.startStakeName;
	}

	public void setStartStakeName(String startStakeName) {
		this.startStakeName = startStakeName;
	}

	public String getEndStake() {
		return this.endStake;
	}

	public void setEndStake(String endStake) {
		this.endStake = endStake;
	}

	public Double getEndStake2() {
		return this.endStake2;
	}

	public void setEndStake2(Double endStake2) {
		this.endStake2 = endStake2;
	}

	public String getEndStakeName() {
		return this.endStakeName;
	}

	public void setEndStakeName(String endStakeName) {
		this.endStakeName = endStakeName;
	}

	public Integer getHistoryNo() {
		return this.historyNo;
	}

	public void setHistoryNo(Integer historyNo) {
		this.historyNo = historyNo;
	}

	public Short getLatestMark() {
		return this.latestMark;
	}

	public void setLatestMark(Short latestMark) {
		this.latestMark = latestMark;
	}

	public Short getRemoveMark() {
		return this.removeMark;
	}

	public void setRemoveMark(Short removeMark) {
		this.removeMark = removeMark;
	}

	public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getRid() {
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public Double getManageLength() {
		return this.manageLength;
	}

	public void setManageLength(Double manageLength) {
		this.manageLength = manageLength;
	}

	public Double getQytclc() {
		return this.qytclc;
	}

	public void setQytclc(Double qytclc) {
		this.qytclc = qytclc;
	}

	public String getGldj() {
		return this.gldj;
	}

	public void setGldj(String gldj) {
		this.gldj = gldj;
	}

	public Double getNpjjdcjtl() {
		return this.npjjdcjtl;
	}

	public void setNpjjdcjtl(Double npjjdcjtl) {
		this.npjjdcjtl = npjjdcjtl;
	}

	public Double getLjkd() {
		return this.ljkd;
	}

	public void setLjkd(Double ljkd) {
		this.ljkd = ljkd;
	}

	public Double getQjxzxbj() {
		return this.qjxzxbj;
	}

	public void setQjxzxbj(Double qjxzxbj) {
		this.qjxzxbj = qjxzxbj;
	}

	public Double getZdcp() {
		return this.zdcp;
	}

	public void setZdcp(Double zdcp) {
		this.zdcp = zdcp;
	}

	public Double getLmkd() {
		return this.lmkd;
	}

	public void setLmkd(Double lmkd) {
		this.lmkd = lmkd;
	}

	public Double getLmgjsntlc() {
		return this.lmgjsntlc;
	}

	public void setLmgjsntlc(Double lmgjsntlc) {
		this.lmgjsntlc = lmgjsntlc;
	}

	public Double getLmgjlqtlc() {
		return this.lmgjlqtlc;
	}

	public void setLmgjlqtlc(Double lmgjlqtlc) {
		this.lmgjlqtlc = lmgjlqtlc;
	}

	public Double getLmcgjlc() {
		return this.lmcgjlc;
	}

	public void setLmcgjlc(Double lmcgjlc) {
		this.lmcgjlc = lmcgjlc;
	}

	public Double getLmzjlc() {
		return this.lmzjlc;
	}

	public void setLmzjlc(Double lmzjlc) {
		this.lmzjlc = lmzjlc;
	}

	public Double getLmdjlc() {
		return this.lmdjlc;
	}

	public void setLmdjlc(Double lmdjlc) {
		this.lmdjlc = lmdjlc;
	}

	public Double getKlhlc() {
		return this.klhlc;
	}

	public void setKlhlc(Double klhlc) {
		this.klhlc = klhlc;
	}

	public Double getYlhlc() {
		return this.ylhlc;
	}

	public void setYlhlc(Double ylhlc) {
		this.ylhlc = ylhlc;
	}

}