package com.highwaycenter.glz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.glz.dao.GlzjbxxDao;
import com.highwaycenter.glz.model.HGlzGlzJbxx;
@Repository("glzjbxxdao")
public class GlzjbxxDaoImpl extends BaseDaoDB<HGlzGlzJbxx> implements GlzjbxxDao{

	@Override
	public BaseQueryRecords selectList(Integer page, Integer rows) {
		return super.find(new HGlzGlzJbxx(), page, rows);
	}

	@Override
	public BaseQueryRecords selectPropertyList(String key) {
		String sql = "select a."+key +" from h_glz_glz_jbxx a";
		return super.find(new SQL(sql));
	}

	@Override
	public BaseQueryRecords selectByHql(HQL hql) {
		return super.find(hql);
	}

	@Override
	public BaseQueryRecords selectByHql(HQL hql, Integer page, Integer rows) {
		return super.find(hql, page, rows);
	}

	@Override
	public List<Object[]> selectPropertyList(String key1, String key2) {
		String sql = "select distinct "+key1 +", "+key2+" from h_glz_glz_jbxx ";
		return (List<Object[]>) super.find(new SQL(sql)).getData();
	}

	@Override
	public HGlzGlzJbxx selectUnique(String stationId) {
		
		return super.findUnique(new HGlzGlzJbxx(), "id", stationId);
	}

	@Override
	public String selectGljgmc(String stationId) {
		String sql = "select b.gljgmc from h_jc_gljg as b,h_jc_jgglzdy as a where a.gljgdm = b.gljgdm and a.stationId='"+stationId+"'";
		return (String) super.findUnique(new SQL(sql));
	}

	@Override
	public String selectGlzName(String stationId) {
		String sql ="select name from h_glz_glz_jbxx where id='"+stationId+"'";
		return (String) super.findUnique(new SQL(sql));
	}

}
