package com.highwaycenter.bean;

import java.util.List;

public class JsQxNodes   implements java.io.Serializable {


	private static final long serialVersionUID = -1038977109779438191L;
	private int jsbh;
	private String jsmc;
	private List<TreeNodes> qxzlist;
	private List<QxNodes> qxlist;
	private String qxs;
	
	public JsQxNodes(){
		
	}
	
	public JsQxNodes(int jsbh,String jsmc){
		this.jsbh = jsbh;
		this.jsmc = jsmc;
				
	}
	
	
	
	public int getJsbh() {
		return jsbh;
	}
	public void setJsbh(int jsbh) {
		this.jsbh = jsbh;
	}
	public String getJsmc() {
		return jsmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	public List<TreeNodes> getQxzlist() {
		return qxzlist;
	}
	public void setQxzlist(List<TreeNodes> qxzlist) {
		this.qxzlist = qxzlist;
	}
	public List<QxNodes> getQxlist() {
		return qxlist;
	}
	public void setQxlist(List<QxNodes> qxlist) {
		this.qxlist = qxlist;
	}

	public String getQxs() {
		return qxs;
	}

	public void setQxs(String qxs) {
		this.qxs = qxs;
	}
	
	
	

}
