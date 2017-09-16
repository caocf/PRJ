package com.huzhouport.integratedquery.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.integratedquery.dao.ShipLibraryDao;
import com.huzhouport.integratedquery.service.ShipLibraryService;
import com.huzhouport.publicuser.model.ShipLibrary;

public class ShipLibraryServiceImpl extends BaseServiceImpl<ShipLibrary> implements ShipLibraryService {
	private ShipLibraryDao shipLibraryDao;
	
	public void setShipLibraryDao(ShipLibraryDao shipLibraryDao) {
		this.shipLibraryDao = shipLibraryDao;
	}
	// 查询条数
	public Map<String, Integer> CountShipNameListByInfo(ShipLibrary shipLibrary, int pageSize)
			throws Exception{

		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.shipLibraryDao.CountShipNameListByInfo(shipLibrary);
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	// 查询本页
	public List<?> SearchShipNameListByInfo(ShipLibrary shipLibrary, int pageNo, int pageSize)throws Exception{

		int beginIndex = (pageNo - 1) * pageSize;
		return this.shipLibraryDao.SearchShipNameListByInfo(shipLibrary, beginIndex,pageSize);
	
	}
}
