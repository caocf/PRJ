package com.ghpublic.entity;

import java.io.Serializable;

/**
 * @author zzq
 * @DateTime 2016年7月19日 下午4:43:34
 * 企业信息实体类
 * 没用到
 */
public class CompanyInfoEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String companyName;//公司名
	private String companyArea;//公司区域
	private String area;//所在地级市
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyArea() {
		return companyArea;
	}
	public void setCompanyArea(String companyArea) {
		this.companyArea = companyArea;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

}
