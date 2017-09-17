package com.highwaycenter.bean;

import java.util.List;

import com.highwaycenter.role.model.HJcJbqx;

public class Fqxz implements java.io.Serializable {


	private static final long serialVersionUID = 3743475703799240876L;
	private Integer qxzbh;
	private String qxzmc ;
	private List<HJcJbqx> jbqxlist;
	private int isChoose;  //0是没有选择 1是选择
	private String jbqxdesc; //基本权限描述
	
	public Fqxz(){
		
	}


	public Integer getQxzbh() {
		return qxzbh;
	}


	public void setQxzbh(Integer qxzbh) {
		this.qxzbh = qxzbh;
	}


	public String getQxzmc() {
		return qxzmc;
	}


	public void setQxzmc(String qxzmc) {
		this.qxzmc = qxzmc;
	}


	public List<HJcJbqx> getJbqxlist() {
		return jbqxlist;
	}


	public void setJbqxlist(List<HJcJbqx> jbqxlist) {
		this.jbqxlist = jbqxlist;
	}


	public Fqxz(Integer qxzbh, String qxzmc, List<HJcJbqx> jbqxlist) {
		super();
		this.qxzbh = qxzbh;
		this.qxzmc = qxzmc;
		this.jbqxlist = jbqxlist;
	}
	public Fqxz(Integer qxzbh, String qxzmc) {
		super();
		this.qxzbh = qxzbh;
		this.qxzmc = qxzmc;
		//this.isChoose=0;
		
	}

	public int getIsChoose() {
		return isChoose;
	}


	public void setIsChoose(int isChoose) {
		this.isChoose = isChoose;
	}
	
	 public boolean equals(Object obj) {  
	        if (!(obj instanceof Fqxz))  
	            return false;     
	        if (obj == this)  
	            return true;  
	        return this.qxzbh == ((Fqxz) obj).qxzbh;  
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
