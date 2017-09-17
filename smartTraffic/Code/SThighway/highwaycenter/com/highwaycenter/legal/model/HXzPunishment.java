package com.highwaycenter.legal.model;


import java.util.Date;

import javax.persistence.Transient;

public class HXzPunishment  implements java.io.Serializable {

	private static final long serialVersionUID = -8678470716535059827L;
	private Integer xzcfbh;//行政处罚编号                          ROW_ID
	private String xzcfjgdm;//行政处罚机关代码                是新增字段（浙江省什么的）
	private String xzcfjdwh;//行政处罚决定文号               XZCFWS_CODE
	private String ajmc;//案件名称                                         XZCFWS_NAME
	private String cfrxm;//处罚人姓名                                   BXZCF_NAME
	private String wfqyzjjgdm;//违法企业组织机构代码   CertNum
	private String fddbrxm;//法定代表人姓名                      BXZCF_LEGALMAN
	private String zywfss;//主要违法事实                            XZCF_ZY
	private String xzcf;//行政处罚履行方式和期限             PerformanceAndTimelimit
	private String xzcfzl;//行政处罚的种类和依据              basis
	private Date  xzcfrqdate;//行政处罚日期                     XZCF_DATE
	private String bz;//备注                                                        note
	private String ouGuid;//部门Guid               XZCF_ORGCODE
	private String ouName;//部门名称                                     XZCF_ORGNAME
	private Integer state;//状态,本地同步状态表示   1:未同步  ；0：已同步            Sync_sign
    private Integer dateversion;                     //数据版本，初始值是1   XZCF_DATAVERSION
    private Integer isdelete;;//是否作废：0：正常 1：删除
    private String synctime;//上次同步时间
    private String reginname;//地区名称：嘉兴                                     REGINNAME
    private String xzcftype;//被行政处罚类型                                       BXZCF_TYPE
    private String xzcfcardtype;//被行政处罚对象证件类型             APPLY_CARDTYPE
    private String xzcfcardnumber;//被行政处罚对象证件号码        BXZCF_CARDNUMBER
    private String legalmanIdcard;//被处罚单位法人身份证            BXZCF_LEGALMANCARDNUMBER
    private String xzcfitemid;//行政处罚权利事项编号                     XZCF_CODE
    @Transient
    private String xzcfname;//行政处罚权利事项名称                           XZCF_NAME
    @Transient
    private String xzcfcode;//行政处罚内部编码（权利事项唯一编码）     
    @Transient
    private String xzcfrq;//行政处罚日期  
	@Transient
	private String xzcfjgmc;//行政处罚机构名称
	@Transient
	private String xzcftypename;//被处罚类型名称
	@Transient
	private String xzcfcardtypename;//被行政处罚对象证件类型名称
	private int xzcfly;//行政处罚来源

	public static final int XZCFLY_ZC=1;   //治超站数据
	public static final int XZCFLY_LOACL=0;  //本地录入数据
	public HXzPunishment(){
		
	}
	
	public HXzPunishment(Integer xzcfbh, String xzcfjgdm, String xzcfjdwh,
			String ajmc, String cfrxm, String wfqyzjjgdm, String fddbrxm,
			String xzcfrq, String bz) {
		super();
		this.xzcfbh = xzcfbh;
		this.xzcfjgdm = xzcfjgdm;
		this.xzcfjdwh = xzcfjdwh;
		this.ajmc = ajmc;
		this.cfrxm = cfrxm;
		this.wfqyzjjgdm = wfqyzjjgdm;
		this.fddbrxm = fddbrxm;
		this.xzcfrq = xzcfrq;
		this.bz = bz;
	}

	public Integer getXzcfbh() {
		return xzcfbh;
	}

	public void setXzcfbh(Integer xzcfbh) {
		this.xzcfbh = xzcfbh;
	}

	public String getXzcfjgdm() {
		return xzcfjgdm;
	}

	public void setXzcfjgdm(String xzcfjgdm) {
		this.xzcfjgdm = xzcfjgdm;
	}

	public String getXzcfjdwh() {
		return xzcfjdwh;
	}

	public void setXzcfjdwh(String xzcfjdwh) {
		this.xzcfjdwh = xzcfjdwh;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getCfrxm() {
		return cfrxm;
	}

	public void setCfrxm(String cfrxm) {
		this.cfrxm = cfrxm;
	}

	public String getWfqyzjjgdm() {
		return wfqyzjjgdm;
	}

	public void setWfqyzjjgdm(String wfqyzjjgdm) {
		this.wfqyzjjgdm = wfqyzjjgdm;
	}

	public String getFddbrxm() {
		return fddbrxm;
	}

	public void setFddbrxm(String fddbrxm) {
		this.fddbrxm = fddbrxm;
	}

	public String getZywfss() {
		return zywfss;
	}

	public void setZywfss(String zywfss) {
		this.zywfss = zywfss;
	}

	public String getXzcf() {
		return xzcf;
	}

	public void setXzcf(String xzcf) {
		this.xzcf = xzcf;
	}

	public String getXzcfzl() {
		return xzcfzl;
	}

	public void setXzcfzl(String xzcfzl) {
		this.xzcfzl = xzcfzl;
	}

	public String getXzcfrq() {
		return xzcfrq;
	}

	public void setXzcfrq(String xzcfrq) {
		this.xzcfrq = xzcfrq;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getOuGuid() {
		return ouGuid;
	}

	public void setOuGuid(String ouGuid) {
		this.ouGuid = ouGuid;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	

	public Integer getDateversion() {
		return dateversion;
	}

	public void setDateversion(Integer dateversion) {
		this.dateversion = dateversion;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public String getSynctime() {
		return synctime;
	}

	public void setSynctime(String synctime) {
		this.synctime = synctime;
	}

	public String getReginname() {
		return reginname;
	}

	public void setReginname(String reginname) {
		this.reginname = reginname;
	}

	public String getXzcftype() {
		return xzcftype;
	}

	public void setXzcftype(String xzcftype) {
		this.xzcftype = xzcftype;
	}

	public String getXzcfcardtype() {
		return xzcfcardtype;
	}

	public void setXzcfcardtype(String xzcfcardtype) {
		this.xzcfcardtype = xzcfcardtype;
	}

	public String getXzcfcardnumber() {
		return xzcfcardnumber;
	}

	public void setXzcfcardnumber(String xzcfcardnumber) {
		this.xzcfcardnumber = xzcfcardnumber;
	}

	public String getLegalmanIdcard() {
		return legalmanIdcard;
	}

	public void setLegalmanIdcard(String legalmanIdcard) {
		this.legalmanIdcard = legalmanIdcard;
	}

	public String getXzcfitemid() {
		return xzcfitemid;
	}

	public void setXzcfitemid(String xzcfitemid) {
		this.xzcfitemid = xzcfitemid;
	}

	public String getXzcfname() {
		return xzcfname;
	}

	public void setXzcfname(String xzcfname) {
		this.xzcfname = xzcfname;
	}

	public String getXzcfcode() {
		return xzcfcode;
	}

	public void setXzcfcode(String xzcfcode) {
		this.xzcfcode = xzcfcode;
	}

	public String getXzcfjgmc() {
		return xzcfjgmc;
	}

	public void setXzcfjgmc(String xzcfjgmc) {
		this.xzcfjgmc = xzcfjgmc;
	}

	public String getXzcftypename() {
		return xzcftypename;
	}

	public void setXzcftypename(String xzcftypename) {
		this.xzcftypename = xzcftypename;
	}

	public String getXzcfcardtypename() {
		return xzcfcardtypename;
	}

	public void setXzcfcardtypename(String xzcfcardtypename) {
		this.xzcfcardtypename = xzcfcardtypename;
	}

	public Date getXzcfrqdate() {
		return xzcfrqdate;
	}

	public void setXzcfrqdate(Date xzcfrqdate) {
		this.xzcfrqdate = xzcfrqdate;
	}

	public int getXzcfly() {
		return xzcfly;
	}

	public void setXzcfly(int xzcfly) {
		this.xzcfly = xzcfly;
	}





	
	
	

}
