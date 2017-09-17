package com.highwaycenter.role.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

/**
 * HJcQxz entity. @author MyEclipse Persistence Tools
 */

public class HJcQxz implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 8775219207996937469L;
	private Integer qxzbh;
	private String qxzmc;
	@Transient
	private String jbqxdesc; //基本权限描述
	

	// Constructors

	/** default constructor */
	public HJcQxz() {
	}
	public HJcQxz(Integer qxzbh,String qxzmc) {
		this.qxzbh = qxzbh;
		this.qxzmc = qxzmc;
	}

	/** full constructor */
	public HJcQxz(Integer qxzbh) {
		this.qxzbh = qxzbh;

	}
	
	/** full constructor */
	public HJcQxz(String qxzmc) {
		this.qxzmc = qxzmc;

	}

	// Property accessors

	public Integer getQxzbh() {
		return this.qxzbh;
	}

	public void setQxzbh(Integer qxzbh) {
		this.qxzbh = qxzbh;
	}

	public String getQxzmc() {
		return this.qxzmc;
	}

	public void setQxzmc(String qxzmc) {
		this.qxzmc = qxzmc;
	}
	

    public boolean equals(Object obj) {  
        if (!(obj instanceof HJcJbqx))  
            return false;     
        if (obj == this)  
            return true;  
        return this.qxzbh == ((HJcQxz) obj).qxzbh;  
    }  
   
    public int hashCode(){  
        return qxzbh;//简单原则  
    }
	public String getJbqxdesc() {
		return jbqxdesc;
	}
	public void setJbqxdesc(String jbqxdesc) {
		this.jbqxdesc = jbqxdesc;
	}  



}