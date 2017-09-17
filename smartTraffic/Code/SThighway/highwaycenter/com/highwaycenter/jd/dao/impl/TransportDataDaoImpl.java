package com.highwaycenter.jd.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.jd.dao.TransportDataDao;
import com.highwaycenter.jd.model.HJdTransportData;

@Repository("transdatadao")
public class TransportDataDaoImpl extends BaseDaoDB<HJdTransportData> implements TransportDataDao {

	@Override
	public void save(HJdTransportData transdata) {
		super.save(transdata);
		
	}

	@Override
	public int selectCountByDate(String date) {
		return super.count(new HJdTransportData(), "tjsj",date).intValue();
	}

	@Override
	public void deleteByDate(String date) {
		String sql = String.format("delete from h_jd_transdata where tjsj ='%s'",date);
		super.delete(new SQL(sql));
		
	}
		

}
