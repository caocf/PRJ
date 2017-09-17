package com.highwaycenter.glz.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.highwaycenter.glz.dao.GlzHdgkDao;
import com.highwaycenter.glz.model.HGlzHdgk;

@Repository("glzhdgkdao")
public class GlzHdgkDaoImpl extends BaseDaoDB<HGlzHdgk> implements GlzHdgkDao{

	@Override
	public BaseQueryRecords selectGlzHdgkList(Short removeMark, Integer page,
			Integer rows, String stationId, String selectvalue) {
		StringBuffer hql = new StringBuffer("select new HGlzHdgk(a.id, a.code, a.stake, a.stake2,a.type,a.culvertLong)"+
			" from HGlzHdgk a where 1=1 ");
		if(removeMark!=null&&!removeMark.equals("")){
			hql.append(" and removeMark="+removeMark+" ");
		}
		if(stationId!=null&&!stationId.trim().equals("")){
			hql.append(" and stationId='"+stationId+"' ");
		}
		if(selectvalue!=null&&!selectvalue.trim().equals("")){
			hql.append(" and a.code like '%"+selectvalue+"%' ");
		}
		
		return super.find(new HQL(hql.toString()),page,rows);
	}

	@Override
	public HGlzHdgk selectGlzHdgkXq(String id) {
		
		return super.findUnique(new HGlzHdgk(), "id",id);
	}

}
