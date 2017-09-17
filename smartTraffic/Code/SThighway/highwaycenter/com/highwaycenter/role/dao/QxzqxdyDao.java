package com.highwaycenter.role.dao;
import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcQxzqxdy;



public interface QxzqxdyDao {
	public void save(HJcQxzqxdy qxzqxdy);
	//public void update(HJcGlry glry);
	public void delete(HJcQxzqxdy qxzqxdy);
	public int deleteByQxz(int qxzbh);
	public BaseQueryRecords findByHql(HQL hql);
	public int deleteByBH(int bh);
	public BaseQueryRecords findBySql(SQL sql);
    public List<HJcJbqx> findByQxz(Integer qxzbh);
    public List<Integer> findqxbhByQxz(Integer qxzbh);
    public List<Integer> findqxzbhByQx(List<Integer> qxbhs);
    public List<HJcJbqx> findOtherQxz();
}
