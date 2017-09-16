package net.hxkg.ship;

import java.io.Serializable;

public class BreakRulesModel implements Serializable
{
//	CFLB	处罚类别，每位0表示无此类，1表示有此类。从第一位到第10位的处罚类别顺序分别为：
//	第1位：警告
//	第2位：罚款
//	第3位：暂扣证照
//	第4位：吊销证照
//	第5位：没收船舶
//	第6位：没收非法财物
//	第7位：没收违法所得
//	第8位：责令停产停业
//	第9位：行政拘留
//	第10位：其他

	private String AJLB;    //	AJLB	案件类别
	private String AY;		//	AY	案由
	private String CFLB;    //处罚类别，每位0表示无此类，1表示有此类。从第一位到第10位的处罚类别顺序分别为：
	private String CFYJ;    //	CFYJ	处罚意见		
	private String CFTK;    //	CFTK	处罚条款
	private String FADD;    //	FADD	法案地点	
	private String FASJ;   //	FASJ	发案时间
	private String JARQ;   //	JARQ	结案日期	
	private String SFCF;   //	SFCF	是否处罚，0表示否，1表示是		
	private String ID;		//	ID	ID
	private String SFJA;	//	SFJA	是否结案，0表示否，1表示是
	private String SLH;		//	SLH	受理号
	private String SLSJ;	//	SLSJ	受理时间	
	private String WFNR;	//	WFNR	违法内容
	private String WFTK;    //	WFTK	违反条款
	private String ZFSCBH;  //	ZFSCBH	执法手册编号
	private String ZWCM;    //	ZWCM	中文船名
	private String ZYSS;    //	ZYSS	主要事实
	public String getAJLB() {
		return AJLB;
	}
	public void setAJLB(String AJLB) {
		this.AJLB = AJLB;
	}
	public String getAY() {
		return AY;
	}
	public void setAY(String AY) {
		this.AY = AY;
	}
	public String getCFLB() {
		return CFLB;
	}
	public void setCFLB(String CFLB) {
		this.CFLB = CFLB;
	}	
	public String getCFYJ() {
		return CFYJ;
	}
	public void setCFYJ(String CFYJ) {
		this.CFYJ = CFYJ;
	}
	public String getCFTK() {
		return CFTK;
	}
	public void setCFTK(String CFTK) {
		this.CFTK = CFTK;
	}	
	public String getFADD() {
		return FADD;
	}
	public void setFADD(String FADD) {
		this.FADD = FADD;
	}	
	public String getFASJ() {
		return FASJ;
	}
	public void setFASJ(String FASJ) {
		this.FASJ = FASJ;
	}	
	public String getJARQ() {
		return JARQ;
	}
	public void setJARQ(String JARQ) {
		this.JARQ = JARQ;
	}	
	public String getSFCF() {
		return SFCF;
	}
	public void setSFCF(String SFCF) {
		this.SFCF = SFCF;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
	public String getSFJA() {
		return SFJA;
	}
	public void setSFJA(String SFJA) {
		this.SFJA = SFJA;
	}

	public String getSLH() {
		return SLH;
	}
	public void setSLH(String SLH) {
		this.SLH = SLH;
	}
	
	
	public String getSLSJ() {
		return SLSJ;
	}
	public void setSLSJ(String SLSJ) {
		this.SLSJ = SLSJ;
	}
	
	
	
	
	public String getWFNR() {
		return WFNR;
	}
	public void setWFNR(String WFNR) {
		this.WFNR = WFNR;
	}
	
	
	
	public String getWFTK() {
		return WFTK;
	}
	public void setWFTK(String WFTK) {
		this.WFTK = WFTK;
	}
	
	public String getZFSCBH() {
		return ZFSCBH;
	}
	public void setZFSCBH(String ZFSCBH) {
		this.ZFSCBH = ZFSCBH;
	}
	
	public String getZWCM() {
		return ZWCM;
	}
	public void setZWCM(String ZWCM) {
		this.ZWCM = ZWCM;
	}
	
	public String getZYSS() {
		return ZYSS;
	}
	public void setZYSS(String ZYSS) {
		this.ZYSS = ZYSS;
	}
	
	
}
