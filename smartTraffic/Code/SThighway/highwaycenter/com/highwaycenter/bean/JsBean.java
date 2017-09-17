package com.highwaycenter.bean;

import java.util.List;

import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcQxz;

public class JsBean implements java.io.Serializable {

	private static final long serialVersionUID = -1078793211814292534L;
	private Integer jsbh;
	private String jsmc;
	private String qxzs;
	private String qxs;
	private List<HJcJbqx> qxlist;
	private List<HJcQxz> qxzlist;
	
	
	public JsBean(Integer jsbh, String jsmc) {
		super();
		this.jsbh = jsbh;
		this.jsmc = jsmc;
	}
	public JsBean(Integer jsbh, String jsmc,  String qxs,String qxzs) {
		super();
		this.jsbh = jsbh;
		this.jsmc = jsmc;
		this.qxzs = qxzs;
		this.qxs = qxs;
	}
	public JsBean() {
		super();
	}
	
	public JsBean(Integer jsbh) {
		super();
		this.jsbh = jsbh;
	}
	public Integer getJsbh() {
		return jsbh;
	}
	public void setJsbh(Integer jsbh) {
		this.jsbh = jsbh;
	}
	public String getJsmc() {
		return jsmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	public String getQxzs() {
		return qxzs;
	}
	public void setQxzs(String qxzs) {
		this.qxzs = qxzs;
	}
	public String getQxs() {
		return qxs;
	}
	public void setQxs(String qxs) {
		this.qxs = qxs;
	}
	
	public List<HJcJbqx> getQxlist() {
		return qxlist;
	}
	public void setQxlist(List<HJcJbqx> qxlist) {
		this.qxlist = qxlist;
	}
	public List<HJcQxz> getQxzlist() {
		return qxzlist;
	}
	public void setQxzlist(List<HJcQxz> qxzlist) {
		this.qxzlist = qxzlist;
	}
	
	
	
	
	
	
	
	

}
