package com.huzhouport.illegal.model;


public class Abnormal implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int aid;
	private String shipname;
	private String gatename;
	private String abdate;
	private String  route;
	private String  AIS;
	private String blacklist;
	private String arrearage;
	private String report;
	private String illegal;
	private String cert;
	private String process;
	private String pass;
	private String remark;
	private String status;
	//private String potname;
	private String shipid;
	private String fromid;
	
	public String getFromid()
	{
		return fromid;
	}
	public void setFromid(String fromid) 
	{
		this.fromid = fromid;
	}
	public String getShipid()
	{
		return shipid;
	}
	public void setShipid(String shipid) 
	{
		this.shipid = shipid;
	}
	/*public String getPotname()
	{
		return potname;
	}
	public void setPotname(String potname) 
	{
		this.potname = potname;
	}*/
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getProcess()
	{
		return process;
	}
	public void setProcess(String process)
	{
		this.process=process;
	}
	public String getPass()
	{
		return pass;
	}
	public void setPass(String pass)
	{
		this.pass=pass;
	}

	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark=remark;
	}

	public String getCert()
	{
		return cert;
	}
	public void setCert(String cert)
	{
		this.cert=cert;
	}
	public String getIllegal()
	{
		return illegal;
	}
	public void setIllegal(String illegal)
	{
		this.illegal=illegal;
	}

	public String getReport()
	{
		return report;
	}
	public void setReport(String report)
	{
		this.report=report;
	}
	public String getArrearage()
	{
		return arrearage;
	}
	public void setArrearage(String arrearage)
	{
		this.arrearage=arrearage;
	}
	public String getBlacklist()
	{
		return blacklist;
	}
	public void setBlacklist(String blacklist)
	{
		this.blacklist=blacklist;
	}
	public String getAIS()
	{
		return AIS;
	}
	public void setAIS(String AIS)
	{
		this.AIS=AIS;
	}
	
	public String getRoute()
	{
		return route;
	}
	public void setRoute(String route)
	{
		this.route=route;
	}
	
	public String getAbdate()
	{
		return abdate;
	}
	public void setAbdate(String abdate)
	{
		this.abdate=abdate;
	}
	
	public int getAid()
	{
		return aid;
	}
	public void setAid(int aid)
	{
		this.aid=aid;
	}
	public String getShipname()
	{
		return shipname;
		
	}
	public void setShipname(String shipname)
	{
		this.shipname=shipname;
	}
	public String getGatename()
	{
		return gatename;
		
	}
	public void setGatename(String gatename)
	{
		this.gatename=gatename;
	}
}
