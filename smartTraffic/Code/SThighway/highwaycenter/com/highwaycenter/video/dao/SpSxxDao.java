package com.highwaycenter.video.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.video.model.HSpSxx;

public interface SpSxxDao {
	public void saveSpSxx(HSpSxx sxx);
	
	public BaseQueryRecords selectSxxList(int page,int rows);
	
	public List<String> selectCameraName(String selectvalue);
	
	public void updateSpSxx(HSpSxx sxx);
	
	public void deleteSpSxx(int sxxId);
	
	public int countSameSpsxx(HSpSxx spsxx);
	
	public HSpSxx selectUnique(int sxxId);

}
