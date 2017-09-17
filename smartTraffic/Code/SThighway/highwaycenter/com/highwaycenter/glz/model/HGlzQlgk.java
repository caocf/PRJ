package com.highwaycenter.glz.model;


public class HGlzQlgk implements java.io.Serializable {


	private static final long serialVersionUID = -2895752098746468769L;
	private String id;
	private String code;
	private String name;
	private String stake;
	private Double stake2;
	private String versionId;
	private Short removeMark;
	private Short latestMark;
	private Integer historyNo;
	private String rid;
	private String qlqc;
	private String kjzc;
	private String dkzdkj;
	private String kjzh;
	private String qlqk;
	private String qmjk;
	private String akjfldm;
	private String akjflle;
	private String zqsbgzjglxdm;
	private String zqsbgzjglx;
	private String zqsbgzcldm;
	private String zqsbgzclmc;
	private String qdlxdm;
	private String qdlx;
	private String sjhzdjdm;
	private String sjhzdj;
	private String kydwlxdm;
	private String kydwlx;
	private String kydwmc;
	private String dhdj;
	private String dtfzsslx;
	private String sfhtlj;
	private String jsdwmc;
	private String sjdwmc;
	private String sgdwmc;
	private String jldwmc;
	private String xjnd;
	private String jctcrq;
	private String gydwxzdm;
	private String gydwmc;
	private String jgdwmc;
	private String sfxzdm;
	private String sfxz;
	private String pddjdm;
	private String pddj;
	private String pdrq;
	private String gznd;
	private String wknd;
	private String gzbw;
	private String gcxz;
	private String sfbbzxm;
	private String dqzybhdm;
	private String dqzybhbw;
	private String dqzybh;
	private String jtgzcsdm;
	private String jtgzcs;
	private String bz;
	private String bridgePeriodicalCheckType;

	// Constructors
	public HGlzQlgk(String id, String code, String name, String stake,Double stake2,String qlqc,String zqsbgzjglxdm,
        String zqsbgzjglx) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.stake = stake;
		this.stake2 = stake2;		
		this.qlqc = qlqc;		
		this.zqsbgzjglxdm = zqsbgzjglxdm;
		this.zqsbgzjglx = zqsbgzjglx;
	
	}
	/** default constructor */
	public HGlzQlgk() {
	}

	/** minimal constructor */
	public HGlzQlgk(String id) {
		this.id = id;
	}

	/** full constructor */
	public HGlzQlgk(String id, String code, String name, String stake,
			Double stake2, String versionId, Short removeMark,
			Short latestMark, Integer historyNo, String rid, String qlqc,
			String kjzc, String dkzdkj, String kjzh, String qlqk, String qmjk,
			String akjfldm, String akjflle, String zqsbgzjglxdm,
			String zqsbgzjglx, String zqsbgzcldm, String zqsbgzclmc,
			String qdlxdm, String qdlx, String sjhzdjdm, String sjhzdj,
			String kydwlxdm, String kydwlx, String kydwmc, String dhdj,
			String dtfzsslx, String sfhtlj, String jsdwmc, String sjdwmc,
			String sgdwmc, String jldwmc, String xjnd, String jctcrq,
			String gydwxzdm, String gydwmc, String jgdwmc, String sfxzdm,
			String sfxz, String pddjdm, String pddj, String pdrq, String gznd,
			String wknd, String gzbw, String gcxz, String sfbbzxm,
			String dqzybhdm, String dqzybhbw, String dqzybh, String jtgzcsdm,
			String jtgzcs, String bz, String bridgePeriodicalCheckType) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.stake = stake;
		this.stake2 = stake2;
		this.versionId = versionId;
		this.removeMark = removeMark;
		this.latestMark = latestMark;
		this.historyNo = historyNo;
		this.rid = rid;
		this.qlqc = qlqc;
		this.kjzc = kjzc;
		this.dkzdkj = dkzdkj;
		this.kjzh = kjzh;
		this.qlqk = qlqk;
		this.qmjk = qmjk;
		this.akjfldm = akjfldm;
		this.akjflle = akjflle;
		this.zqsbgzjglxdm = zqsbgzjglxdm;
		this.zqsbgzjglx = zqsbgzjglx;
		this.zqsbgzcldm = zqsbgzcldm;
		this.zqsbgzclmc = zqsbgzclmc;
		this.qdlxdm = qdlxdm;
		this.qdlx = qdlx;
		this.sjhzdjdm = sjhzdjdm;
		this.sjhzdj = sjhzdj;
		this.kydwlxdm = kydwlxdm;
		this.kydwlx = kydwlx;
		this.kydwmc = kydwmc;
		this.dhdj = dhdj;
		this.dtfzsslx = dtfzsslx;
		this.sfhtlj = sfhtlj;
		this.jsdwmc = jsdwmc;
		this.sjdwmc = sjdwmc;
		this.sgdwmc = sgdwmc;
		this.jldwmc = jldwmc;
		this.xjnd = xjnd;
		this.jctcrq = jctcrq;
		this.gydwxzdm = gydwxzdm;
		this.gydwmc = gydwmc;
		this.jgdwmc = jgdwmc;
		this.sfxzdm = sfxzdm;
		this.sfxz = sfxz;
		this.pddjdm = pddjdm;
		this.pddj = pddj;
		this.pdrq = pdrq;
		this.gznd = gznd;
		this.wknd = wknd;
		this.gzbw = gzbw;
		this.gcxz = gcxz;
		this.sfbbzxm = sfbbzxm;
		this.dqzybhdm = dqzybhdm;
		this.dqzybhbw = dqzybhbw;
		this.dqzybh = dqzybh;
		this.jtgzcsdm = jtgzcsdm;
		this.jtgzcs = jtgzcs;
		this.bz = bz;
		this.bridgePeriodicalCheckType = bridgePeriodicalCheckType;
	}



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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRid() {
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getQlqc() {
		return this.qlqc;
	}

	public void setQlqc(String qlqc) {
		this.qlqc = qlqc;
	}

	public String getKjzc() {
		return this.kjzc;
	}

	public void setKjzc(String kjzc) {
		this.kjzc = kjzc;
	}

	public String getDkzdkj() {
		return this.dkzdkj;
	}

	public void setDkzdkj(String dkzdkj) {
		this.dkzdkj = dkzdkj;
	}

	public String getKjzh() {
		return this.kjzh;
	}

	public void setKjzh(String kjzh) {
		this.kjzh = kjzh;
	}

	public String getQlqk() {
		return this.qlqk;
	}

	public void setQlqk(String qlqk) {
		this.qlqk = qlqk;
	}

	public String getQmjk() {
		return this.qmjk;
	}

	public void setQmjk(String qmjk) {
		this.qmjk = qmjk;
	}

	public String getAkjfldm() {
		return this.akjfldm;
	}

	public void setAkjfldm(String akjfldm) {
		this.akjfldm = akjfldm;
	}

	public String getAkjflle() {
		return this.akjflle;
	}

	public void setAkjflle(String akjflle) {
		this.akjflle = akjflle;
	}

	public String getZqsbgzjglxdm() {
		return this.zqsbgzjglxdm;
	}

	public void setZqsbgzjglxdm(String zqsbgzjglxdm) {
		this.zqsbgzjglxdm = zqsbgzjglxdm;
	}

	public String getZqsbgzjglx() {
		return this.zqsbgzjglx;
	}

	public void setZqsbgzjglx(String zqsbgzjglx) {
		this.zqsbgzjglx = zqsbgzjglx;
	}

	public String getZqsbgzcldm() {
		return this.zqsbgzcldm;
	}

	public void setZqsbgzcldm(String zqsbgzcldm) {
		this.zqsbgzcldm = zqsbgzcldm;
	}

	public String getZqsbgzclmc() {
		return this.zqsbgzclmc;
	}

	public void setZqsbgzclmc(String zqsbgzclmc) {
		this.zqsbgzclmc = zqsbgzclmc;
	}

	public String getQdlxdm() {
		return this.qdlxdm;
	}

	public void setQdlxdm(String qdlxdm) {
		this.qdlxdm = qdlxdm;
	}

	public String getQdlx() {
		return this.qdlx;
	}

	public void setQdlx(String qdlx) {
		this.qdlx = qdlx;
	}

	public String getSjhzdjdm() {
		return this.sjhzdjdm;
	}

	public void setSjhzdjdm(String sjhzdjdm) {
		this.sjhzdjdm = sjhzdjdm;
	}

	public String getSjhzdj() {
		return this.sjhzdj;
	}

	public void setSjhzdj(String sjhzdj) {
		this.sjhzdj = sjhzdj;
	}

	public String getKydwlxdm() {
		return this.kydwlxdm;
	}

	public void setKydwlxdm(String kydwlxdm) {
		this.kydwlxdm = kydwlxdm;
	}

	public String getKydwlx() {
		return this.kydwlx;
	}

	public void setKydwlx(String kydwlx) {
		this.kydwlx = kydwlx;
	}

	public String getKydwmc() {
		return this.kydwmc;
	}

	public void setKydwmc(String kydwmc) {
		this.kydwmc = kydwmc;
	}

	public String getDhdj() {
		return this.dhdj;
	}

	public void setDhdj(String dhdj) {
		this.dhdj = dhdj;
	}

	public String getDtfzsslx() {
		return this.dtfzsslx;
	}

	public void setDtfzsslx(String dtfzsslx) {
		this.dtfzsslx = dtfzsslx;
	}

	public String getSfhtlj() {
		return this.sfhtlj;
	}

	public void setSfhtlj(String sfhtlj) {
		this.sfhtlj = sfhtlj;
	}

	public String getJsdwmc() {
		return this.jsdwmc;
	}

	public void setJsdwmc(String jsdwmc) {
		this.jsdwmc = jsdwmc;
	}

	public String getSjdwmc() {
		return this.sjdwmc;
	}

	public void setSjdwmc(String sjdwmc) {
		this.sjdwmc = sjdwmc;
	}

	public String getSgdwmc() {
		return this.sgdwmc;
	}

	public void setSgdwmc(String sgdwmc) {
		this.sgdwmc = sgdwmc;
	}

	public String getJldwmc() {
		return this.jldwmc;
	}

	public void setJldwmc(String jldwmc) {
		this.jldwmc = jldwmc;
	}

	public String getXjnd() {
		return this.xjnd;
	}

	public void setXjnd(String xjnd) {
		this.xjnd = xjnd;
	}

	public String getJctcrq() {
		return this.jctcrq;
	}

	public void setJctcrq(String jctcrq) {
		this.jctcrq = jctcrq;
	}

	public String getGydwxzdm() {
		return this.gydwxzdm;
	}

	public void setGydwxzdm(String gydwxzdm) {
		this.gydwxzdm = gydwxzdm;
	}

	public String getGydwmc() {
		return this.gydwmc;
	}

	public void setGydwmc(String gydwmc) {
		this.gydwmc = gydwmc;
	}

	public String getJgdwmc() {
		return this.jgdwmc;
	}

	public void setJgdwmc(String jgdwmc) {
		this.jgdwmc = jgdwmc;
	}

	public String getSfxzdm() {
		return this.sfxzdm;
	}

	public void setSfxzdm(String sfxzdm) {
		this.sfxzdm = sfxzdm;
	}

	public String getSfxz() {
		return this.sfxz;
	}

	public void setSfxz(String sfxz) {
		this.sfxz = sfxz;
	}

	public String getPddjdm() {
		return this.pddjdm;
	}

	public void setPddjdm(String pddjdm) {
		this.pddjdm = pddjdm;
	}

	public String getPddj() {
		return this.pddj;
	}

	public void setPddj(String pddj) {
		this.pddj = pddj;
	}

	public String getPdrq() {
		return this.pdrq;
	}

	public void setPdrq(String pdrq) {
		this.pdrq = pdrq;
	}

	public String getGznd() {
		return this.gznd;
	}

	public void setGznd(String gznd) {
		this.gznd = gznd;
	}

	public String getWknd() {
		return this.wknd;
	}

	public void setWknd(String wknd) {
		this.wknd = wknd;
	}

	public String getGzbw() {
		return this.gzbw;
	}

	public void setGzbw(String gzbw) {
		this.gzbw = gzbw;
	}

	public String getGcxz() {
		return this.gcxz;
	}

	public void setGcxz(String gcxz) {
		this.gcxz = gcxz;
	}

	public String getSfbbzxm() {
		return this.sfbbzxm;
	}

	public void setSfbbzxm(String sfbbzxm) {
		this.sfbbzxm = sfbbzxm;
	}

	public String getDqzybhdm() {
		return this.dqzybhdm;
	}

	public void setDqzybhdm(String dqzybhdm) {
		this.dqzybhdm = dqzybhdm;
	}

	public String getDqzybhbw() {
		return this.dqzybhbw;
	}

	public void setDqzybhbw(String dqzybhbw) {
		this.dqzybhbw = dqzybhbw;
	}

	public String getDqzybh() {
		return this.dqzybh;
	}

	public void setDqzybh(String dqzybh) {
		this.dqzybh = dqzybh;
	}

	public String getJtgzcsdm() {
		return this.jtgzcsdm;
	}

	public void setJtgzcsdm(String jtgzcsdm) {
		this.jtgzcsdm = jtgzcsdm;
	}

	public String getJtgzcs() {
		return this.jtgzcs;
	}

	public void setJtgzcs(String jtgzcs) {
		this.jtgzcs = jtgzcs;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getBridgePeriodicalCheckType() {
		return this.bridgePeriodicalCheckType;
	}

	public void setBridgePeriodicalCheckType(String bridgePeriodicalCheckType) {
		this.bridgePeriodicalCheckType = bridgePeriodicalCheckType;
	}

}