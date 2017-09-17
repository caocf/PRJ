package com.highwaycenter.jd.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.jd.model.HJdJxdczxx;
import com.highwaycenter.jd.model.HJdJxzdjwd;

public interface JxdczxxDao {
	public HJdJxdczxx selectJxdczxx(String dczbh);
	public List<Object[]> selectOptionlist(String hql);
	public HJdJxdczxx selectJxdczxxSimple(String dczbh);
	public BaseQueryRecords selectJxdczxxList(String condition,int page,int rows,String selectId);
	public HJdJxzdjwd selectJWd(String gczbh);

}
