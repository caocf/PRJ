package com.huzhouport.attendace.dao.impl;

import java.util.List;

import com.huzhouport.attendace.dao.LocationDao;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.dao.impl.BaseDaoImpl;

public class LocationDaoImpl extends BaseDaoImpl<Location> implements LocationDao{

	public int countPageLocationInfo(Location location, String condition)
			throws Exception {
		String hql = "select count(*) from Location l ";
		if (condition != "") {
			hql += "where (" + condition + ")";
		}
		return this.countEForeignKey(location, hql);
	}

	public List<?> searchLocationInfo(Location location, String condition,
			String sequence, int startSet, int maxSet) throws Exception {
		String hql = " from Location l " ;
		if (condition != "") {
			hql += "where (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(location, hql, startSet, maxSet);
		return list;
	}

}
