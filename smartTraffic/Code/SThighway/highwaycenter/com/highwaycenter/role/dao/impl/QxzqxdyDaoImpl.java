package com.highwaycenter.role.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.role.dao.QxzqxdyDao;
import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcQxzqxdy;
//xiugai
@Repository("qxzqxdydao")
public class QxzqxdyDaoImpl extends BaseDaoDB<HJcQxzqxdy>  implements QxzqxdyDao{



	@Override
	public void save(HJcQxzqxdy qxzqxdy) {
		super.save(qxzqxdy);
		
	}

	@Override
	public void delete(HJcQxzqxdy qxzqxdy) {
		super.delete(qxzqxdy);
		
	}

	@Override
	public int deleteByQxz(int qxzbh) {
		String sql = "delete from h_jc_qxzqxdy where qxzbh = "+qxzbh;
		return super.delete(new SQL(sql));
		
	}
	
	@Override
	public int deleteByBH(int bh) {
		String sql = "delete from h_jc_qxzqxdy where qxbh = "+bh;
		return super.delete(new SQL(sql));
		
	}

	@Override
	public BaseQueryRecords findByHql(HQL hql) {
		return super.find(hql);
	}

	@Override
	public BaseQueryRecords findBySql(SQL sql) {
	
		return super.find(sql);
	}

	@Override
	public List<HJcJbqx> findByQxz(Integer qxzbh) {
		String hql = "from HJcJbqx a where a.qxbh in("+
	                 "select b.qxbh from HJcQxzqxdy b where b.qxzbh ="+qxzbh+")";
		
		return (List<HJcJbqx>) super.find(new HQL(hql)).getData();
	}

	@Override
	public List<Integer> findqxbhByQxz(Integer qxzbh) {
		String hql = "select qxbh from HJcQxzqxdy where qxzbh="+qxzbh;
		return (List<Integer>) super.find(new HQL(hql)).getData();
	}

	@Override
	public List<Integer> findqxzbhByQx(List<Integer> qxbhs) {
		String hql = "select distinct qxzbh from HJcQxzqxdy where qxbh IN (:alist)";
		return (List<Integer>) super.find(new HQL(hql,qxbhs)).getData();
	}

	@Override
	public List<HJcJbqx> findOtherQxz() {
		String hql="FROM HJcJbqx a WHERE a.qxbh NOT IN (SELECT b.qxbh from HJcQxzqxdy b )";  
		
		return (List<HJcJbqx>) super.find(new HQL(hql)).getData();
	}

	


}
