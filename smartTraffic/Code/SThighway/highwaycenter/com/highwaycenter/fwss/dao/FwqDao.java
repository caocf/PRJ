package com.highwaycenter.fwss.dao;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.fwss.model.HFwFwq;

public interface FwqDao {
	public BaseQueryRecords selectFwqList(int page,int rows,String selectValue,String selectId);

	public HFwFwq selectFwq(int fwqbh);
	
	public Object saveAndReturn(HFwFwq fwq);
	
	public void update(HFwFwq fwq);
	
	public void delete(int fwqbh);
	
	public int  countSameName(String fwqmc,Integer fwqbh);
	
	public BaseQueryRecords selectUnion(int page, int rows,String selectValue,String fwssType);
	
    public String selectnameByBh(String tablename, String selectKey,
			String bhkey, Object bhvalue);
}
