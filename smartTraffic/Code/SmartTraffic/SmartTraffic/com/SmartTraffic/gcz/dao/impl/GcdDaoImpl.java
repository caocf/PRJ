package com.SmartTraffic.gcz.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.SmartTraffic.gcz.dao.GcdDao;
import com.SmartTraffic.multiple.model.HGcGcd;
import com.common.dao.impl.BaseDaoDB;

@Repository("gcdDao")
public class GcdDaoImpl extends BaseDaoDB implements GcdDao{

	@Override
	public List<HGcGcd> showGcdInfo() {
		return (List<HGcGcd>) super.find(new HGcGcd()).getData();
	}

	@Override
	public Object showTrafficByGcdDay(String gcdid, String strdate) {
		String hql = "select sum(j.rgcbsl) as rgcbsl,sum(j.rgcbdw) "
				+ " as rgcbdw from h_gc_gcd d, h_gc_cbsj j where d.gcdid=j.gcdid and j.gcdid='?' and j.tgsj='?'";
	try{
		final Query q = getCurrentSession().createSQLQuery(hql);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		List list = q.list();	
		if(list==null||list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	} catch (Exception e1) {
		
		System.out.println(e1.getMessage());
		return null;
	}
	}

	@Override
	public Object showTrafficByGcdMonth(String gcdid, String monthstartdate,
			String monthenddate) {
		String hql = "select sum(j.rgcbsl),sum(j.rgcbdw) from h_gc_gcd d, h_gc_cbsj j where d.gcdid=j.gcdid and j.gcdid='?' and j.tgsj>='?' and j.tgsj<'?'";
	try{
		final Query q = getCurrentSession().createSQLQuery(hql);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
		List list = q.list();	
		if(list==null||list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	} catch (Exception e1) {
		
		System.out.println(e1.getMessage());
		return null;
	}
	}

	@Override
	public Object showTrafficByGcdYear(String gcdid, String yearstartdate,
			String yearenddate) {
		String hql = "select sum(j.rgcbsl),sum(j.rgcbdw) from h_gc_gcd d, h_gc_cbsj j where d.gcdid=j.gcdid and j.gcdid='?' and j.tgsj>='?' and j.tgsj<='?'";
		try{
			final Query q = getCurrentSession().createSQLQuery(hql);
			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
			List list = q.list();	
			if(list==null||list.size()==0){
				return null;
			}else{
				return list.get(0);
			}
		} catch (Exception e1) {
			
			System.out.println(e1.getMessage());
			return null;
		}
	}

	@Override
	public HGcGcd showGcdByPK(String gcdid) {
		return (HGcGcd) super.findUnique(new HGcGcd(), "gcdid", gcdid);
	}
	
}