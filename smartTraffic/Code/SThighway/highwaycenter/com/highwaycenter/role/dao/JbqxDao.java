package com.highwaycenter.role.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.role.model.HJcJbqx;

public interface JbqxDao {

	public HJcJbqx findById(int jbqx_id); 
	public BaseQueryRecords findAll();
	public List<Integer> selectQxbhList();
	public List<HJcJbqx> selectQxNoChosed(Integer qxzbh);
	public List<HJcJbqx> selectQxNoChosed();
	
	
}
