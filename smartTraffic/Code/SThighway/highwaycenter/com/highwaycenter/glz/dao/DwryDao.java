package com.highwaycenter.glz.dao;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.glz.model.HGlzJxDwry;

public interface DwryDao {
	
	public BaseQueryRecords selectDwrygkList(Integer page,Integer rows,String stationId,String selectvalue);
	
	public HGlzJxDwry selectDwryXq(String id);

}
