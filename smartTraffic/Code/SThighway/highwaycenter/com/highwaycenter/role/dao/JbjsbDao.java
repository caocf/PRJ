package com.highwaycenter.role.dao;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.role.model.HJcJbjsb;



public interface JbjsbDao {
	public void save(HJcJbjsb jbjsb);
	public Integer saveAndReturn(HJcJbjsb jbjsb);
	public void update(HJcJbjsb jbjsb);
	public void delete(HJcJbjsb jbjsb);
	public HJcJbjsb findById(int jbjsb_id); 
	public int selectCountByBh(Integer jsbh);
	public BaseQueryRecords findAll();
	public BaseQueryRecords jsListAll(int page,int rows);
    public int countSameName(String jsmc,Integer jsbh);
    public String selectnameByBh(String tablename, String selectKey,
			String bhkey, Object bhvalue);
}
