package com.highwaycenter.fwss.dao;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.fwss.model.HFwSfz;

public interface SfzDao {
	public BaseQueryRecords selectSfzList(int page,int rows,String selectValue,String selectId);
	
	public  HFwSfz selectSfz(int sfzbh);
	
	public Object saveAndReturn(HFwSfz sfz);
	
	public void update(HFwSfz sfz);
	
	public void delete(int sfzbh);
	
	public int  countSameName(String sfzmc,Integer sfzbh);
	
    public String selectnameByBh(String tablename, String selectKey,
			String bhkey, Object bhvalue);

}
