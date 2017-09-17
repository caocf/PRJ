package com.highwaycenter.gljg.dao.impl;
//xiugai
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.gljg.dao.GljgDao;
import com.highwaycenter.gljg.model.HJcGljg;
import com.highwaycenter.gljg.model.HJcGlry;

@Repository("gljgdao")
public class GljgDaoImpl extends BaseDaoDB<HJcGljg> implements GljgDao{

	@Override
	public void save(HJcGljg gljg) {
		super.save(gljg);
		
	}
	
	@Override
	public String saveAndReturn(HJcGljg gljg){
		return (String) super.saveAndReturn(gljg);
		
	}

	@Override
	public void update(HJcGljg gljg) {
		super.update(gljg);
		
	}

	@Override
	public void delete(HJcGljg gljg) {
		super.delete(gljg);
		
	}

	@Override
	public HJcGljg findById(String gljg_id) {
		
		return super.findUnique(new HJcGljg(), "gljgdm",gljg_id);
	}

	@Override
	public BaseQueryRecords findByKey(String key, Object value, int page,
			int rows) {
		
		return super.find(new HJcGljg(), key, value, page, rows);
	}

	@Override
	public BaseQueryRecords findBySql(SQL sql) {
		return super.find(sql);
	}

	@Override
	public BaseQueryRecords findByHQL(HQL hql) {
		return super.find(hql);
	}

	@Override
	public BaseQueryRecords findByKey(String key, Object value) {
		
		return super.find(new HJcGljg(), key, value);
	}

	@Override
	public BaseQueryRecords findByHQL(HQL hql, int page, int rows) {
		return super.find(hql, page, rows);
	}

	@Override
	public int countByKey(String key, Object value) {
		return super.count(new HJcGljg(), key, value).intValue();
		
	}
	
	@Override
	public int countById(String gljgdm) {
		String sql = "select * from h_jc_gljg where GLJGDM = '"+  gljgdm +"'";		
		// TODO Autre
		return super.count(new SQL(sql)).intValue();
		
	}
	
	@Override
	public HJcGljg findByBhAndJgmc(String jgdm, String jgmc) {
		try {
			Criteria criteria = super.getCriteria(new HJcGljg());

			criteria.add(Restrictions.ne("gljgdm", jgdm));   //人员编号不为其本身
			criteria.add(Restrictions.eq("gljgmc", jgmc));
			List<HJcGljg> list = criteria.list();
			if(list.size()>0){
				return (HJcGljg)list.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public HJcGljg selectFjg() {
		HQL hql = new HQL("from HJcGljg where (sjgljgdm is null or sjgljgdm ='')");
		return (HJcGljg) super.findUnique(hql);
	}

	@Override
	public void updateName(String gljgdm,String name) {
		String sql  ="update h_jc_gljg set gljgmc = '"+name+"'  where gljgdm= '"+gljgdm+"'";
		super.executeSql(new SQL(sql));
	}

	@Override
	public String selectSjgljgdm(String jgdm) {
		String sql ="select sjgljgdm from h_jc_gljg where gljgdm= '"+jgdm+"'";
		return (String)super.findUnique(new SQL(sql));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HJcGljg> listall() {	
		return (List<HJcGljg>) super.findOrderBy(new HJcGljg(), "sjgljgdm", false).getData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HJcGljg> xzcflistall() {
		return (List<HJcGljg>) super.findOrderBy(new HJcGljg(), "sfxzcfjg", 1,"sjgljgdm", false).getData();
	}

	@Override
	public Integer selectSfxzcfjg(String gljgbh) {
		String sql = "select sfxzcfjg from h_jc_gljg where gljgdm='"+gljgbh+"'";
		return (Integer)super.findUnique(new SQL(sql));
	}

	@Override
	public BaseQueryRecords findByssgljgandbz(String sjgljgdm, String sfzxgljg) {
		String hql = "from HJcGljg where sjgljgdm ='"+sjgljgdm+"' ";
		if(sfzxgljg!=null&&sfzxgljg.equals("1")){
			hql = hql+" and sfxzcfjg = 1 ";
		}
		return super.find(new HQL(hql));
	}

}
