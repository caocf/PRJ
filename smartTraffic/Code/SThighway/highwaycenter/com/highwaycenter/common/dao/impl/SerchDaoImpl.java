package com.highwaycenter.common.dao.impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.common.dao.SearchDao;
@Repository("searchdao")
public class  SerchDaoImpl extends BaseDaoDB<T> implements SearchDao{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectKey(String tableName,String keyname,String valuename) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select "+keyname+","+valuename+" from "+tableName;
		
		List<Object[]> objlist = (List<Object[]>) super.find(new SQL(sql)).getData();
		
		if(objlist!=null&&objlist.size()>0){
			Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				selectListBean selectbean = new selectListBean();
				Object[] obj = it.next();
				selectbean.setIdObj(obj[0]);
				selectbean.setName((String)obj[1]);
				list.add(selectbean);
			}
		}
		
		return list;
	}

	@Override
	public List<Object> selectSingleKey(String tablename, String keyname) {
		String sql = "select distinct "+keyname+" from "+tablename+" where "+keyname+" is not null and "+keyname+"<> '"+"' ";
		List<Object> list = new ArrayList<Object>();
		List<Object> objlist = (List<Object>) super.find(new SQL(sql)).getData();
		if(objlist!=null&&objlist.size()>0){
			for(int i=0;i<objlist.size();i++){
				selectListBean selectbean = new selectListBean();
				selectbean.setIdObj(objlist.get(i));
				selectbean.setName((String)objlist.get(i));
				list.add(selectbean);
			}
		}
		
		
		return list;
	}

	@Override
	public BaseQueryRecords selectByHql(String hql, int page, int rows) {
		
		return super.find(new HQL(hql),page,rows);
	}

}
