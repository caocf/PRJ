package com.highwaycenter.bean;

public class FwssBean  implements java.io.Serializable {
	private static final long serialVersionUID = 6728988631578841455L;
	
	
	private String bh;//服务设施编号
	private String mc;//服务设施名称
	private String ssgs;//所属公司
	private String xlmc;//线路名称
	private String fwssType;//服务设施类别 
	
	
	public FwssBean(){
		
	}
	
	public FwssBean(String bh, String mc, String ssgs, String xlmc,
			String fwssType) {
		super();
		this.bh = bh;
		this.mc = mc;
		this.ssgs = ssgs;
		this.xlmc = xlmc;
		this.fwssType = fwssType;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getSsgs() {
		return ssgs;
	}
	public void setSsgs(String ssgs) {
		this.ssgs = ssgs;
	}
	public String getXlmc() {
		return xlmc;
	}
	public void setXlmc(String xlmc) {
		this.xlmc = xlmc;
	}
	public String getFwssType() {
		return fwssType;
	}
	public void setFwssType(String fwssType) {
		this.fwssType = fwssType;
	}
	
	
	
	
	
	

}
