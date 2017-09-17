package com.SmartTraffic.video.dao;

import java.util.List;
import java.util.Map;

import com.SmartTraffic.video.model.CameraDto;
import com.SmartTraffic.video.model.STCamera;
import com.SmartTraffic.video.model.STControlUnit;
import com.SmartTraffic.video.model.STRegion;

public interface VideoDao {
	
	public void saveCameras(List<STCamera> cameras);
	public void saveRegions(List<STRegion> regions);
	public void saveControlUnits(List<STControlUnit> units);
	public void deleteAllTable(String tablename);
	
	public List<STCamera> selectSTCameraByRegion(int regionId);
	public List<STCamera> selectSTCameraByUnit(int unitId);
	public List<STRegion> selectRegionByUnit(int unitId);
	public List<STControlUnit> selectControlUnit();
	public void updateLonlat(int cameraId,float longitude,float latitude);
	public void saveOrUpdateLonLat(int cameraId,float longitute);
	public Object selectCameraItem(int cameraId);
	
	public List<?> selectRegionList(int unitId);
	public List<?> selectControlList();
	public List<?> selectCamera(int regionId);
	public  List<?> selectAllCamera();
	public void plUpdate(List<CameraDto> list);
	
	public List<?> selectAllSameCamera();
	public void updateSameCamera(List<Map<String,Object>> list);
}

