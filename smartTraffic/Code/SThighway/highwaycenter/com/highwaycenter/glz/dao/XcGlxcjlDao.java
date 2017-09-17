package com.highwaycenter.glz.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.GlxcldBean;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.glz.model.HGlzXcGlxcjl;

public interface XcGlxcjlDao {
	public BaseQueryRecords selectXcGlxcjl(Integer page,Integer rows,String stationId,String selectvalue);
	
    public HGlzXcGlxcjl selectXcGlxcjlXq(String id);
    
    public List<GlxcldBean> selectXcGlxcldXq(String roadRecordId);
    
    public List<selectListBean> selectInspectDateList();

}
