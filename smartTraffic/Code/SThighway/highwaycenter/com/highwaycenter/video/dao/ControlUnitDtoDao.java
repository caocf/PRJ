package com.highwaycenter.video.dao;

import java.util.List;

import com.highwaycenter.video.model.HSpControlUnitDTO;

public interface ControlUnitDtoDao {
	
	public void save(HSpControlUnitDTO center);
	
	public void deleteall();
	
	public List<HSpControlUnitDTO> selectAllControlUnit();

}
