package com.huzhouport.portNavigation.model;

public class ModelPortNavigation {
	private int titleId;// 标题
	private String titile;// 标题
	private String date;// 时间
	private String url;// 连接
	private String contenct;// 返回详细内容
	private String photoUrl;// 图片地址
	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public String getTitile() {
		return titile;
	}

	public void setTitile(String titile) {
		this.titile = titile;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContenct() {
		return contenct;
	}

	public void setContenct(String contenct) {
		this.contenct = contenct;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}