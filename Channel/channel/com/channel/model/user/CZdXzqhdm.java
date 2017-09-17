package com.channel.model.user;

import java.util.Date;

import com.common.utils.tree.model.TreeNode;

/**
 * CZdXzqhdm entity. @author MyEclipse Persistence Tools
 */

public class CZdXzqhdm extends TreeNode implements java.io.Serializable {

	private String code;
	private Date createtime;
	private Date updatetime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public CZdXzqhdm() {
		super();
	}

	public CZdXzqhdm(String code, Date createtime, Date updatetime) {
		super();
		this.code = code;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}
	
	public CZdXzqhdm(String code, int type, String name,
			Date createtime, Date updatetime) {
		super();
		this.code = code;
		super.setType(type);
		super.setName(name);
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public CZdXzqhdm(int id, String code, int type, String name,
			Date createtime, Date updatetime) {
		super();
		super.setId(id);
		this.code = code;
		super.setType(type);
		super.setName(name);
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

}