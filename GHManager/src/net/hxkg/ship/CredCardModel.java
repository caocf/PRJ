package net.hxkg.ship;

public class CredCardModel 
{	
	private String ZSMC;//证书名称
	private String FZRQ;//发证日期
	private String YXRQ;//有效日期
	private String ZSBH;//ZSBH
	
	public String getZSBH()
	{
		return ZSBH;
	}
	public void setZSBH(String ZSBH)
	{
		this.ZSBH=ZSBH;
	}
	
	public String getZSMC() {
		return ZSMC;
	}
	public void setZSMC(String ZSMC) {
		this.ZSMC = ZSMC;
	}
	public String getFZRQ() {
		return FZRQ;
	}
	public void setFZRQ(String FZRQ) {
		this.FZRQ = FZRQ;
	}
	public String getYXRQ() {
		return YXRQ;
	}
	public void setYXRQ(String YXRQ) {
		this.YXRQ = YXRQ;
	}
	

}