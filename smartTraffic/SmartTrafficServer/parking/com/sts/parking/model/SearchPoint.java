package com.sts.parking.model;

public class SearchPoint implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String loc; // 中心点经纬度
	private String range; // 范围
	private String key; // 关键字
	private String startPosition; // Request page number 请求的页数
	private String pageNumber; // Record number for one page 请求的每页记录数
	private String Json; // 返回的数据格式（1、xml 其他、json）
	private String poiCat;// poi类型

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(String startPosition) {
		this.startPosition = startPosition;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getJson() {
		return Json;
	}

	public void setJson(String json) {
		Json = json;
	}

	public String getPoiCat() {
		return poiCat;
	}

	public void setPoiCat(String poiCat) {
		this.poiCat = poiCat;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

}
