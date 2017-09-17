package com.highwaycenter.video.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.video.model.HSpCameraDTO;
import com.highwaycenter.video.model.HSpSxx;

public interface CameraDtoDao {
	public void saveCameraDto(HSpCameraDTO deviceDto);

	public void deleteAll();
	
	public List<HSpCameraDTO> selectCameraList(Integer deviceId);
	
	public HSpCameraDTO selectCamera(Integer cameraId);
	
	public void saveCameraInfo(Integer cameraId,Integer regionId);
	
	public void deleteallCamerainfo();
	
	public List<HSpCameraDTO> selectCameralistByRegion(Integer regionId);
	
	public List<Integer> selectNoCameraByRegion();
	
	public Integer selectDir(String lxdm,String fxm);
	

	
}
