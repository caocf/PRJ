package com.highwaycenter.jd.dao;

import com.highwaycenter.jd.model.HJdTransportData;

public interface TransportDataDao {
	public void save(HJdTransportData transdata);
	public int  selectCountByDate(String date);
	public void deleteByDate(String date);

}
