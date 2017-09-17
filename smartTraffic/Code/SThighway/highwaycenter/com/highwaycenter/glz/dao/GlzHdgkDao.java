package com.highwaycenter.glz.dao;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.glz.model.HGlzHdgk;

public interface GlzHdgkDao {
public BaseQueryRecords selectGlzHdgkList(Short removeMark,Integer page,Integer rows,String stationId,String selectvalue);
	
	public HGlzHdgk selectGlzHdgkXq(String id);

}
