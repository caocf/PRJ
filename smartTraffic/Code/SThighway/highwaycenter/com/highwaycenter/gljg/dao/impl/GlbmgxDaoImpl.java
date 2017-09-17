package com.highwaycenter.gljg.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.TreeNodes;
import com.highwaycenter.bzbx.model.HBzbxBzbx;
import com.highwaycenter.gljg.dao.GlbmgxDao;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGlbmgx;

@Repository("glbmgxdao")
public class GlbmgxDaoImpl extends BaseDaoDB<HJcGlbmgx> implements GlbmgxDao{

	@Override
	public void save(HJcGlbmgx glbmgx) {
		super.save(glbmgx);
		
	}

	@Override
	public void delete(HJcGlbmgx glbmgx) {
		super.delete(new HJcGlbmgx());
		
	}

	@Override
	public BaseQueryRecords findBySql(SQL sql) {
		return super.find(sql);
	}

	@Override
	public int count(String key, Object value) {
		return super.count(new HJcGlbmgx(), key, value).intValue();
	}

	
	@Override
	public int deleteByBmdm(String bmdm) {
		String sql = "delete from  h_jc_glbmgx where bmdm = '" +bmdm+"'";
		return super.delete(new SQL(sql));
		
	}
	
	@Override
	public List<TreeNodes> findxjbmNode(String sjbmdm) {
		 List<TreeNodes> a =new  ArrayList<TreeNodes>();
		String sql = "select a.bmdm,b.bmmc,a.sjbmdm ,b.ordercolumn from h_jc_glbmgx as a left join h_jc_glbm as "+
				"b on a.bmdm = b.bmdm where a.sjbmdm ='" +sjbmdm+"' order by b.ordercolumn desc";
		List list =  super.getCurrentSession().createSQLQuery(sql)
				.list();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				TreeNodes tr = new TreeNodes(obj[0],obj[1].toString(),obj[2]);
				a.add(tr);
			}
		
			
		}
		return a;
	}

	@Override
	public String findUniqueBySql(SQL sql) {
		
		return (String) super.findUnique(sql);
	}
	@Override	
    public int countSameBm(String sjbmdm,String bmdm,String bmmc){//有部门代码说明要判断是否是自身
		
		StringBuffer sql = new StringBuffer("select count(*) from h_jc_glbmgx as a left join  h_jc_glbm as b on a.bmdm =b.bmdm  where a.sjbmdm='"+sjbmdm+"' and  b.bmmc = '"+bmmc+"' ");
		if(bmdm!=null&&!bmdm.trim().equals("")){
			sql.append(" and a.bmdm <> '"+bmdm+"'");
	      }
		return ((BigInteger)super.find(new SQL(sql.toString())).getData().get(0)).intValue();
	}

	@Override
	public List<HJcGlbm> selectbmlist(String sjbmdm) {
		String sql = "select a.bmdm,b.bmmc,b.ordercolumn from h_jc_glbmgx as a left join h_jc_glbm as "+
				"b on a.bmdm = b.bmdm where a.sjbmdm ='" +sjbmdm+"' order by b.ordercolumn desc";
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("bmdm",StandardBasicTypes.STRING);
			 q.addScalar("bmmc",StandardBasicTypes.STRING);
			 q.setResultTransformer(Transformers.aliasToBean(HJcGlbm.class));
		   	 return q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
