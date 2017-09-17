package com.highwaycenter.gljg.dao;
//xiugai
import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.gljg.model.HJcGljg;

public interface GljgDao {
	public void save(HJcGljg gljg);
	public String saveAndReturn(HJcGljg gljg);
	public void update(HJcGljg gljg);
	public void updateName(String gljgdm,String name);
	public void delete(HJcGljg gljg);
	public HJcGljg findById(String gljg_id); 
	public BaseQueryRecords findByKey(String key, Object value,int page,int rows);
	public BaseQueryRecords findByKey(String key, Object value);
	public BaseQueryRecords findBySql(SQL sql);
	public BaseQueryRecords findByHQL(HQL hql);
	public BaseQueryRecords findByHQL(HQL hql,int page,int rows);
	public int countByKey(String key,Object value);
	public int countById(String gljgdm);
	public HJcGljg findByBhAndJgmc(String jgdm, String jgmc);
	public HJcGljg selectFjg();
	public String selectSjgljgdm (String jgdm);
	public List<HJcGljg> listall();
	public List<HJcGljg> xzcflistall();
	public Integer selectSfxzcfjg(String gljgbh);
	public BaseQueryRecords findByssgljgandbz(String sjgljgdm,String sfzxgkjg);
}
