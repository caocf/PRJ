package com.highwaycenter.role.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.Fqxz;
import com.highwaycenter.role.model.HJcQxz;


public interface QxzDao {
	public void save(HJcQxz qxz);
	public Integer saveAndReturn(HJcQxz qxz);
	public void update(HJcQxz qxz);
	public void delete(HJcQxz qxz);
	public void deleteByBh(int qxzbh);
	public HJcQxz findById(int qxz_id);
	public BaseQueryRecords findAll();
	public BaseQueryRecords findAllPage(int page,int rows);
	public List<Integer> selectQxzbhList();
	public int selectCountByBh(Integer qxzbh);
	public BaseQueryRecords findBySql(SQL sql);
	public int countSameName(String qxzmc,Integer qxzbh);
	public List<Fqxz> findAlltoBean();
	public String selectnameByBh(String tablename, String selectKey,
			String bhkey, Object bhvalue);
}
