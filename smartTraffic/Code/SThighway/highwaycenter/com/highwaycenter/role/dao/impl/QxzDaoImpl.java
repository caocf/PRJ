package com.highwaycenter.role.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.Fqxz;
import com.highwaycenter.role.dao.QxzDao;
import com.highwaycenter.role.model.HJcJbjsb;
import com.highwaycenter.role.model.HJcQxz;

@Repository("qxzdao")
public class QxzDaoImpl extends BaseDaoDB<HJcQxz> implements QxzDao{

	@Override
	public void save(HJcQxz qxz) {
		 super.save(qxz);
		
	}

	@Override
	public Integer saveAndReturn(HJcQxz qxz){
		
		return (Integer) super.saveAndReturn(qxz);
	}
	
	@Override
	public void update(HJcQxz qxz) {
		super.update(qxz);
		
	}

	@Override
	public void delete(HJcQxz qxz) {
		super.delete(qxz);
		
	}

	@Override
	public HJcQxz findById(int qxz_id) {
		  return super.findUnique(new HJcQxz(), "qxzbh", qxz_id);
	}

	@Override
	public BaseQueryRecords findAll() {
		// TODO Auto-generated method stub
		return super.find(new HJcQxz());
	}

	@Override
	public List<Integer> selectQxzbhList() {
		String sql = "select qxzbh from h_jc_qxz ";
		List<Integer> bhlist = (List<Integer>) super.find(new SQL(sql)).getData();
		return bhlist;
	}

	@Override
	public int selectCountByBh(Integer qxzbh) {
		Long qxzcount = super.count(new HJcQxz(), "qxzbh", qxzbh);
		return qxzcount.intValue();
	}
	
	@Override
	public BaseQueryRecords findBySql(SQL sql) {
		return super.find(sql);
	}

	@Override
	public int countSameName(String qxzmc, Integer qxzbh) {
		return super.countSameName("qxzmc", qxzmc, "h_jc_qxz", "qxzbh", qxzbh);
	}

	@Override
	public void deleteByBh(int qxzbh) {
		String sql="delete from h_jc_qxz where qxzbh ="+qxzbh;
		super.delete(new SQL(sql));
		
	}
	
	@Override
	public List<Fqxz> findAlltoBean() {
		String hql = "select new com.highwaycenter.bean.Fqxz(a.qxzbh,a.qxzmc) from HJcQxz a";
		return (List<Fqxz>) super.find(new HQL(hql)).getData();
	}

	@Override
	public BaseQueryRecords findAllPage(int page, int rows) {
		return super.find(new HJcQxz(),page,rows);
	}


}
