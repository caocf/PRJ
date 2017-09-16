package com.huzhouport.integratedquery.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.integratedquery.dao.ShipLibraryDao;
import com.huzhouport.publicuser.model.ShipLibrary;

public class ShipLibraryDaoImpl extends BaseDaoImpl<ShipLibrary> implements
		ShipLibraryDao {

	public Integer CountShipNameListByInfo(ShipLibrary shipLibrary)
			throws Exception {
		String hql = "select count(*) from ShipLibrary s where s.shipName like '%"
				+ shipLibrary.getShipName() + "%'";
		return this.countEForeignKey(null, hql);
	}

	public List<?> SearchShipNameListByInfo(ShipLibrary shipLibrary,
			int beginIndex, int pageSize) throws Exception {

		String hql = "from ShipLibrary s where s.shipName like '%"
				+ shipLibrary.getShipName() + "%'";

		List<?> list = this.queryqueryEForeignKey(null, hql, beginIndex,
				pageSize);
		return list;
	}

}
