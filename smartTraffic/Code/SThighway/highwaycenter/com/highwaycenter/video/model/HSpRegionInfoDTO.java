package com.highwaycenter.video.model;

public class HSpRegionInfoDTO  implements java.io.Serializable {

	private static final long serialVersionUID = -273793012752115290L; //区域
	private Integer regionId;  //区域主键（ID）
	private String indexCode;//区域编号
	private String name;//区域名称
	private Integer parentId;//父节点Id
	private Integer controlUnitId;//所属控制中心
	
	public HSpRegionInfoDTO(){
		
	}
	
	
	public HSpRegionInfoDTO(Integer regionId, String indexCode, String name,
			Integer parentId, Integer controlUnitId) {
		super();
		this.regionId = regionId;
		this.indexCode = indexCode;
		this.name = name;
		this.parentId = parentId;
		this.controlUnitId = controlUnitId;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public String getIndexCode() {
		return indexCode;
	}
	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getControlUnitId() {
		return controlUnitId;
	}
	public void setControlUnitId(Integer controlUnitId) {
		this.controlUnitId = controlUnitId;
	}
	
	
	
	
	
	
	

}
