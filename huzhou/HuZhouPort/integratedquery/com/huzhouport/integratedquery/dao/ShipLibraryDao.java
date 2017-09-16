package com.huzhouport.integratedquery.dao;

import java.util.List;

import com.huzhouport.publicuser.model.ShipLibrary;

public interface ShipLibraryDao {
	public Integer CountShipNameListByInfo(ShipLibrary shipLibrary) throws Exception;
	public List<?> SearchShipNameListByInfo(ShipLibrary shipLibrary,int beginIndex,int pageSize) throws Exception;
}
