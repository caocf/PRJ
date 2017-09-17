package com.highwaycenter.gljg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

import com.highwaycenter.log.model.HJcCzrz;
import com.highwaycenter.role.model.HJcRyjs;

/**
 * HJcGlry entity. @author MyEclipse Persistence Tools
 */

public class HJcGlry implements java.io.Serializable {

	private static final long serialVersionUID = -4580630786039660231L;

	
	private Integer rybh;     //人员编号
/*	private HJcGlbm HJcGlbm;  //人员管理部门
*/	private String ryxm;      //人员姓名
	private String rymm;      //人员密码
	private String xmqp;      //姓名全拼
	private String xmpyszm;   //姓名拼音首字母
	private String sjch;      //手机长号
	private String sjdh;     //手机短号


	@Transient
	private String glbms;   //管理部门
	@Transient
	private String zw;      //职位
	@Transient
	private String bgsdh;   //办公室电话


	/** default constructor */
	public HJcGlry(Integer rybh) {
		this.rybh = rybh;
	}

	public HJcGlry() {
	}

	/** full constructor */
	public HJcGlry(HJcGlbm HJcGlbm, String ryxm, String rymm,String xmqp, String xmpyszm,
			String sjch, String sjdh) {
		/*this.HJcGlbm = HJcGlbm;*/
		this.ryxm = ryxm;
		this.rymm = rymm;
		this.xmqp = xmqp;
		this.xmpyszm = xmpyszm;
		this.sjch = sjch;
		this.sjdh = sjdh;
	}

	public Integer getRybh() {
		return this.rybh;
	}

	public void setRybh(Integer rybh) {
		this.rybh = rybh;
	}

	/*public HJcGlbm getHJcGlbm() {
		return this.HJcGlbm;
	}

	public void setHJcGlbm(HJcGlbm HJcGlbm) {
		this.HJcGlbm = HJcGlbm;
	}
*/
	public String getRyxm() {
		return this.ryxm;
	}

	public void setRyxm(String ryxm) {
		this.ryxm = ryxm;
	}
	
	public String getRymm() {
		return rymm;
	}

	public void setRymm(String rymm) {
		this.rymm = rymm;
	}

	public String getXmqp() {
		return this.xmqp;
	}

	public void setXmqp(String xmqp) {
		this.xmqp = xmqp;
	}

	public String getXmpyszm() {
		return this.xmpyszm;
	}

	public void setXmpyszm(String xmpyszm) {
		this.xmpyszm = xmpyszm;
	}

	public String getSjch() {
		return this.sjch;
	}

	public void setSjch(String sjch) {
		this.sjch = sjch;
	}

	public String getSjdh() {
		return this.sjdh;
	}

	public void setSjdh(String sjdh) {
		this.sjdh = sjdh;
	}

	public String getZw() {
		return this.zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	public String getBgsdh() {
		return this.bgsdh;
	}

	public void setBgsdh(String bgsdh) {
		this.bgsdh = bgsdh;
	}

	public String getGlbms() {
		return glbms;
	}

	public void setGlbms(String glbms) {
		this.glbms = glbms;
	}
	



}