package com.huzhouport.electric.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.dangerousgoodsportln.model.Port;

public interface PortDao extends BaseDao<Port> {
	
	//增加
	public String addPortInfo(Port port) throws Exception;
	
	
}
