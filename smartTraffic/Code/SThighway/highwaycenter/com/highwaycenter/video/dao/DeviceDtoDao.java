package com.highwaycenter.video.dao;

import java.util.List;

import com.highwaycenter.video.model.HSpDeviceDTO;

public interface DeviceDtoDao {
	
	public void saveDeviceDto(HSpDeviceDTO deviceDto);
	
	//public void saveCameraDto();
	
	public void updateDtoName(String name,Integer dtoid,String tableName);

	public void deleteAll();
	
	public String selectNetWorkAddr(Integer deviceId);
	
	public List<HSpDeviceDTO> selectDeviceList();
}
