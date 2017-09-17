package com.highwaycenter.video.model;

public class HSpCameraInfoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 6729669290647123888L;
	private Integer cameraId;
	private Integer regionId;
	public Integer getCameraId() {
		return cameraId;
	}
	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	

}
