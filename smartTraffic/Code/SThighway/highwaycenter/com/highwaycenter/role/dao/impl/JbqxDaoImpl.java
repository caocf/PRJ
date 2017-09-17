package com.highwaycenter.role.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.role.dao.JbqxDao;
import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcJsqx;

@Repository("jbqxdao")
public class JbqxDaoImpl extends BaseDaoDB<HJcJbqx> implements JbqxDao{

	

	@Override
	public HJcJbqx findById(int jbqx_id) {
	   return super.findUnique(new HJcJbqx(), "qxbh", jbqx_id);
	}

	@Override
	public BaseQueryRecords findAll() {
		return super.find(new HJcJbqx());
	}

	@Override
	public List<Integer> selectQxbhList() {
		String sql = "select qxbh from h_jc_jbqx ";
		List<Integer> bhlist = (List<Integer>) super.find(new SQL(sql)).getData();
		return bhlist;
	}

	@Override
	public List<HJcJbqx> selectQxNoChosed(Integer qxzbh) {
		StringBuffer hql = new StringBuffer(" from HJcJbqx a where a.qxbh not in "
	                  +"(select b.qxbh from HJcQxzqxdy b where 1=1 ");
		if(qxzbh!=null){
			hql.append(" and b.qxzbh <> "+qxzbh);
		}
		hql.append(" )");
		
		return (List<HJcJbqx>) super.find(new HQL(hql.toString())).getData();
	}

	@Override
	public List<HJcJbqx> selectQxNoChosed() {
		String hql = " from HJcJbqx a where a.qxbh not in "
                +"(select b.qxbh from HJcQxzqxdy b )";
		return (List<HJcJbqx>) super.find(new HQL(hql)).getData();
	}

}
