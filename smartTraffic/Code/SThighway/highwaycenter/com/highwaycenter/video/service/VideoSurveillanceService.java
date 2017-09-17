package com.highwaycenter.video.service;

import java.util.List;

import com.highwaycenter.video.model.HSpDeviceDTO;


public interface VideoSurveillanceService {

	public List selectAllCamera() throws Exception;
	
	public List<HSpDeviceDTO> seletcAllDevice() throws Exception;
	
	public void selectAllDC() throws Exception;
	
	public void selectRegionCamera() throws Exception; 
	
	/**
	 * 获取控制中心
	 */
	public void selectAllControlCenterForList() throws Exception;
	/**
	 * 获取区域列表
	 */
	public void selectAllRegionInfoForList() throws Exception;
	/**
	 * 获取区域下的设备
	 */
	public void selectCameraInfoListByRegionId() throws Exception;
	
	
}
