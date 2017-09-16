package com.gh.modol;

public class CredCardModel {
	
	private String ZSMC;//证书名称
	private String FZRQ;//发证日期
	private String YXRQ;//有效日期
	
	
//	  "title": "危货作业证书",
//      "post": "2015-9-6",
//      "valid": "2015-11-25"
	
	private String title;   //码头版证书名称
	private String post;	//码头版发证日期
	private String valid;   //码头版有效日期
	
	
	public String gettitle() {
		return title;
	}
	public void settitle(String title) {
		this.title = title;
	}
	public String getpost() {
		return post;
	}
	public void setpost(String post) {
		this.post = post;
	}
	public String getvalid() {
		return valid;
	}
	public void setvalid(String valid) {
		this.valid = valid;
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
