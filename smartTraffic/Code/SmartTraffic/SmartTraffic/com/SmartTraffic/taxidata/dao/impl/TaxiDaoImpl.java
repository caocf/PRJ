package com.SmartTraffic.taxidata.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.SmartTraffic.taxidata.dao.TaxiDao;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;

@Repository("taxiDao")
public class TaxiDaoImpl extends BaseDaoDB implements TaxiDao{



	protected List<?> querySQLForMap(String hql) {
		List<?> list = null;
		try {	
			final Query q = getCurrentSession().createSQLQuery(hql);
			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);	
			list = q.list();		
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			
		}
		System.out.println("end.>>>>>>>"+new Date());
		return list;
	}
		
	
   @Override
	public List<?> searchAllTaxis(String date) {
		String hql = "select * from ZHJTADMIN.Z_CL_CZCGPS g,ZHJTADMIN.Z_CL_CZCJBXX j where g.rq>to_date('"+date+"','yyyy-MM-dd hh24:mi:ss') and g.sbid=j.cphm";
		return this.querySQLForMap(hql);
	}


@Override
public long searchAllTaxisCount() {
	String sql = "select count(*) from Z_CL_CZCGPS ";
	return super.count(new SQL(sql));
}


@Override
public long searchActiveCount(String date) {
	String sql = "select count(*) from Z_CL_CZCGPS where RQ>to_date('"+date+"','yyyy-MM-dd hh24:mi:ss') ";
	return super.count(new SQL(sql));
}


@Override
public float searchAvrSpeed(String date) {
	String sql = "select avg(SD)  from Z_CL_CZCGPS where rq>to_date('"+date+"','yyyy-MM-dd hh24:mi:ss') ";
	Object obj = getCurrentSession().createSQLQuery(sql).uniqueResult();
	if(obj!=null){
		BigDecimal bd = (BigDecimal) obj;
		float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		return f;
	}else{
		return 0;
	}
}

}
