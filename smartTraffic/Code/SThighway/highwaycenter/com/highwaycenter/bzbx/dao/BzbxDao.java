package com.highwaycenter.bzbx.dao;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bzbx.model.HBzbxBzbx;

public interface BzbxDao {

	public void save(HBzbxBzbx bzbx);
	public void update(HBzbxBzbx bzbx);
	public void delete(HBzbxBzbx bzbx);
	public void delete(Integer bzbxbh);
	public HBzbxBzbx selectUnique(Integer bzbxbh );
	public BaseQueryRecords selectGkList(int page,int rows,Integer xzqhdm,
			Integer bzbxlxbh,String selectValue,String selectId);
	public int countBybh(Integer bh);
	public String selectTa(Integer bzbxbh);
	public BaseQueryRecords selectGklisCondition(String condition,int page, int rows);
}