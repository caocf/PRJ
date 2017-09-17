package com.channel.model.user;

import java.util.Date;


/**
 * CXtUser entity. @author MyEclipse Persistence Tools
 */

public class CXtUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private String name;
	private String tel;
	private String email;
	private Integer title;
	private Integer jstatus;
	private String lawid;
	private Integer ustatus;
	private Integer department;
	private Date createtime;
	private Date updatetime;

	// Constructors

	/** default constructor */
	public CXtUser() {
	}

	/** minimal constructor */
	public CXtUser(String username, String password, String name,
			Integer title, Integer jstatus, Integer ustatus,
			Integer department, Date createtime, Date updatetime) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.title = title;
		this.jstatus = jstatus;
		this.ustatus = ustatus;
		this.department = department;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	/** full constructor */
	public CXtUser(String username, String password, String name, String tel,
			String email, Integer title, Integer jstatus, String lawid,
			Integer ustatus, Integer department, Date createtime,
			Date updatetime) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.title = title;
		this.jstatus = jstatus;
		this.lawid = lawid;
		this.ustatus = ustatus;
		this.department = department;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTitle() {
		return this.title;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	public Integer getJstatus() {
		return this.jstatus;
	}

	public void setJstatus(Integer jstatus) {
		this.jstatus = jstatus;
	}

	public String getLawid() {
		return this.lawid;
	}

	public void setLawid(String lawid) {
		this.lawid = lawid;
	}

	public Integer getUstatus() {
		return this.ustatus;
	}

	public void setUstatus(Integer ustatus) {
		this.ustatus = ustatus;
	}

	public Integer getDepartment() {
		return this.department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
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

}