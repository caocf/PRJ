package com.ghpublic.entity;

import java.io.Serializable;

/**
 * @author zzq
 * @DateTime 2016年7月21日 上午10:20:10
 * 企业信息 公司名
 */
public class CompanyNameEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	

}
