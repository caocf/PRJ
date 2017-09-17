package com.highwaycenter.video.model;

public class HSpControlUnitDTO implements java.io.Serializable {

	private static final long serialVersionUID = -4258446931148385155L;
    private Integer controlUnitId; //控制单元主键（ID）
    private String indexCode;//控制单元编号
    private Integer parentId;//上级域ID
    private String name;//名称
    private Integer unitLevel;//控制单元所属的级别
	public Integer getControlUnitId() {
		return controlUnitId;
	}
	public void setControlUnitId(Integer controlUnitId) {
		this.controlUnitId = controlUnitId;
	}
	public String getIndexCode() {
		return indexCode;
	}
	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUnitLevel() {
		return unitLevel;
	}
	public void setUnitLevel(Integer unitLevel) {
		this.unitLevel = unitLevel;
	}
	
	
	

}
