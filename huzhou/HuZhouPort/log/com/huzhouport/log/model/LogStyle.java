package com.huzhouport.log.model;

public class LogStyle implements java.io.Serializable {
	   private static final long serialVersionUID = 1L;

	private int styleId;
	private String styleName;
	public int getStyleId() {
		return styleId;
	}
	public void setStyleId(int styleId) {
		this.styleId = styleId;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	
}