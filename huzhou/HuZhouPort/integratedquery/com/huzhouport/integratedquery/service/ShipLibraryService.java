package com.huzhouport.integratedquery.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.publicuser.model.ShipLibrary;

public interface ShipLibraryService {
	// 查询条数
	public Map<String, Integer> CountShipNameListByInfo(ShipLibrary shipLibrary, int pageSize)
			throws Exception;
	// 查询本页
	public List<?> SearchShipNameListByInfo(ShipLibrary shipLibrary, int pageNo, int pageSize)throws Exception;
}
