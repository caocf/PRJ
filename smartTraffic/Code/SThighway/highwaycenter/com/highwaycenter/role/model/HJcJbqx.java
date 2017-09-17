package com.highwaycenter.role.model;

import javax.persistence.Transient;

/**
 * HJcJbqx entity. @author MyEclipse Persistence Tools
 */

public class HJcJbqx implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 319809796763845024L;
	private Integer qxbh;
	private String qxmc;
	
	@Transient
	private int isJsChoose;    //1.角色相关 0，角色无关
	@Transient
	private int isQxzChoose;    //1.权限组相关 0，权限组无关
	@Transient
    private int isAbleChoose;  //1.权限组可选，0权限组不可选
	// Constructors 

	/** default constructor */
	public HJcJbqx() {
	}
	
	public HJcJbqx(Integer qxbh) {
		this.qxbh = qxbh;
	}

	/** full constructor */
	public HJcJbqx(String qxmc) {
		this.qxmc = qxmc;

	}

	// Property accessors

	public Integer getQxbh() {
		return this.qxbh;
	}

	public void setQxbh(Integer qxbh) {
		this.qxbh = qxbh;
	}

	public String getQxmc() {
		return this.qxmc;
	}

	public void setQxmc(String qxmc) {
		this.qxmc = qxmc;
	}

	
    public boolean equals(Object obj) {  
        if (!(obj instanceof HJcJbqx))  
            return false;     
        if (obj == this)  
            return true;  
        return this.qxbh == ((HJcJbqx) obj).qxbh;  
    }  
   
    public int hashCode(){  
        return qxbh;//简单原则  
    }

	public int getIsJsChoose() {
		return isJsChoose;
	}

	public void setIsJsChoose(int isJsChoose) {
		this.isJsChoose = isJsChoose;
	}

	public int getIsQxzChoose() {
		return isQxzChoose;
	}

	public void setIsQxzChoose(int isQxzChoose) {
		this.isQxzChoose = isQxzChoose;
	}

	public int getIsAbleChoose() {
		return isAbleChoose;
	}

	public void setIsAbleChoose(int isAbleChoose) {
		this.isAbleChoose = isAbleChoose;
	}  
  


}