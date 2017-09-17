package com.highwaycenter.glz.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.highwaycenter.glz.dao.GlzQlgkDao;
import com.highwaycenter.glz.model.HGlzQlgk;
@Repository("glzqlgkdao")
public class GlzQlgkDaoImpl extends BaseDaoDB<HGlzQlgk> implements GlzQlgkDao{

	@Override
	public BaseQueryRecords selectGlzQlgkList(Short removeMark, Integer page,
			Integer rows, String stationId, String selectvalue) {
		StringBuffer hql = new StringBuffer("select new HGlzQlgk(a.id, a.code, a.name, a.stake,a.stake2,a.qlqc,a.zqsbgzjglxdm,"+
        "a.zqsbgzjglx) from HGlzQlgk a where 1=1 ");
		if(removeMark!=null&&!removeMark.equals("")){
			hql.append(" and a.removeMark = "+removeMark+" ");
		}
		if(stationId!=null&&!stationId.trim().equals("")){
			/*hql.append(" and a.stationId = '"+stationId+"' ");*/
		}
		if(selectvalue!=null&&!selectvalue.trim().equals("")){
			hql.append(" and a.name like '%"+selectvalue+"%' ");
		}
		return super.find(new HQL(hql.toString()),page,rows);
	}

	@Override
	public HGlzQlgk selectGlzQlgkXq(String id) {
	
		return super.findUnique(new HGlzQlgk(), "id",id);
	}

}
