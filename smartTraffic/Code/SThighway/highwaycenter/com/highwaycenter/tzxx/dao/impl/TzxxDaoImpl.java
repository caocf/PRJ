package com.highwaycenter.tzxx.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.highwaycenter.tzxx.dao.TzxxDao;
import com.highwaycenter.tzxx.model.HTzTzxxb;

@Repository("tzxxdao")
public class TzxxDaoImpl extends BaseDaoDB<HTzTzxxb> implements TzxxDao{

	@Override
	public BaseQueryRecords selectTzxxList(Integer page, Integer rows,
			String columnId, String selectvalue) {
		StringBuffer hql = new StringBuffer("select new HTzTzxxb(a.mainId, a.title,a.createDate,a.updateDate,"
				+ "a.publishDate,a.publishUsername,a.columnName) from HTzTzxxb a where 1=1 ");
		
		if(columnId!=null&&!columnId.trim().equals("")){
			hql.append(" and a.columnId ='"+columnId+"' ");
		}
		if(selectvalue!=null&&!selectvalue.trim().equals("")){
			hql.append(" and (a.title like '%"+selectvalue+"%' or a.columnName like '%"+selectvalue+"%') ");
		}
		hql.append(" order by a.disorder desc");
		return super.find(new HQL(hql.toString()),page,rows);
	}

	@Override
	public HTzTzxxb selectTzxxXq(String main_id) {
		
		return super.findUnique(new HTzTzxxb(), "mainId",main_id);
	}

	@Override
	public List<Object[]> selectProperty(String key1, String key2) {
		String hql="select distinct a."+key1+",a."+key2+" from HTzTzxxb  a";
		List<Object[]> list= (List<Object[]>) super.find(new HQL(hql)).getData();
		return list;
	}

}
