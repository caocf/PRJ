package com.SmartTraffic.video.service;

import com.common.action.result.BaseResult;


public interface VideoService {

	
	public BaseResult selectControlUnit();
	
	public BaseResult selectRegion(int unitId);
	
	public BaseResult selectCamere(int regionId);
	
	public BaseResult updateLonlat(int cameraId,float longitude,float latitude);
	
	public BaseResult selectLonlat(int cameraId);
	
	public BaseResult selectPlayInfo(int cameraId);
	
	public BaseResult tbGlvideo();
	
	public BaseResult tbVideo();
	
	public BaseResult selectAllCamera();

}
