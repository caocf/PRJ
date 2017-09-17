package com.sts.taxi.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.parkingdb.model.ParkingDB;
import com.sts.taxi.model.Taxi;
import com.sts.taxi.model.TaxiOrder;
import com.sts.taxi.model.TaxiPhone;
import com.sts.taxi.model.TaxiReal;

public class TaxiDao {
	HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	HibernateTemplate oraclHibernateTemplate;

	public void setOraclHibernateTemplate(
			HibernateTemplate oraclHibernateTemplate) {
		this.oraclHibernateTemplate = oraclHibernateTemplate;
	}

	public void save(TaxiOrder t) {
		this.hibernateTemplate.save(t);
	}

	public List<TaxiOrder> queryByID(int userid) {
		String hql = "from TaxiOrder t where t.userid=" + userid
				+ " order by t.date DESC";

		return (List<TaxiOrder>) this.hibernateTemplate.find(hql);
	}

	public List<TaxiPhone> queryTaxiPhoneById(String name) {
		String hql = "from TaxiPhone t where t.identify='" + name + "'";

		return (List<TaxiPhone>) this.hibernateTemplate.find(hql);
	}

	// 分页
	protected List<?> querySQL(String hql, int startSet, int maxSet) {
		Session session = null;
		List<?> list = null;
		session = this.oraclHibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createSQLQuery(hql);
			//q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			q.setFirstResult(startSet); // 从第几条开始
			q.setMaxResults(maxSet); // 取出几条
			list = q.list();
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return list;
	}

	protected List<?> querySQLForMap(String hql, int startSet, int maxSet) {
		Session session = null;
		List<?> list = null;
		session = this.oraclHibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createSQLQuery(hql);
			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			q.setFirstResult(startSet); // 从第几条开始
			q.setMaxResults(maxSet); // 取出几条
			list = q.list();
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return list;
	}
	
	
	public List<Taxi> searchTaxiDetailByName(String name) {
		String hql = "from Taxi t where t.cphm ='" + name + "'";

		return (List<Taxi>) this.oraclHibernateTemplate.find(hql);
	}

	public List<?> searchAllTaxis(int page,int num,String date) {
		String hql = "select * from ZHJTADMIN.Z_CL_CZCGPS g,ZHJTADMIN.Z_CL_CZCJBXX j where g.rq>to_date('"+date+"','yyyy-MM-dd hh24:mi:ss') and g.sbid=j.cphm";

		return this.querySQLForMap(hql, (page-1)*num, num);
	}

	public List<?> searchByLocationCircle(int radius, double lan, double lon,
			int num) {
		String hql = "";

		hql = "select * from ( select p.SBID,p.JD,p.WD,GetDistance(p.WD*1.0/1000000,p.JD*1.0/1000000,"
				+ lon
				+ ","
				+ lan
				+ ") as distance from ZHJTADMIN.Z_CL_CZCGPS p order by distance) where distance<="
				+ radius;

		List<?> r = querySQL(hql, 0, num);

		List<TaxiReal> result = new ArrayList<TaxiReal>();

		for (int i = 0; i < r.size(); i++) {
			Object[] objs = (Object[]) r.get(i);

			TaxiReal t = new TaxiReal();
			t.setSbid((String) objs[0]);
			t.setJd(toint(objs[1]));
			t.setWd(toint(objs[2]));
			t.setDistance(todoulbe(objs[3]));

			result.add(t);
		}

		return result;

	}

	private double todoulbe(Object o) {
		try {
			return ((BigDecimal) o).doubleValue();
		} catch (Exception e) {
		}
		return 0;
	}

	private int toint(Object o) {
		try {
			return ((BigDecimal) o).intValue();
		} catch (Exception e) {
		}
		return 0;
	}
}
