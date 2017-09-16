package com.ghpublic.entity;

import java.io.Serializable;

/**
 * @author  zzq
 * 2016年8月10日 下午5:54:36
 * 潮汐信息详情实体类
 */
public class TideDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String port;
	private String freshdate;
	private String standard;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFreshdate() {
		return freshdate;
	}
	public void setFreshdate(String freshdate) {
		this.freshdate = freshdate;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
}
