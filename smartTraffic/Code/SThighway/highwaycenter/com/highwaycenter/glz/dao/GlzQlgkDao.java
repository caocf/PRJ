package com.highwaycenter.glz.dao;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.glz.model.HGlzQlgk;

public interface GlzQlgkDao {
	public BaseQueryRecords selectGlzQlgkList(Short removeMark,Integer page,Integer rows,String stationId,String selectvalue);
	
	public HGlzQlgk selectGlzQlgkXq(String id);

}
