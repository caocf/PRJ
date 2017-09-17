package com.sts.smartbus.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.smartbus.model.BusStation;
import com.sts.smartbus.model.ZxcZdxx;

@SuppressWarnings("all")
public class SmartBusOracleDao 
{

	HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	/**
	 * 通过id查询站点
	 * @param id 站点id
	 * @return 站点列表数据
	 */
	public List<BusStation> queryStationByStationID(int id)
	{
		String hql="from BusStation b where b.Id="+id;
		
		return (List<BusStation>) this.hibernateTemplate.find(hql);
	}
	
	/**
	 * 站名模糊查询
	 * @param name 站名
	 * @param containBM 是否模糊匹配别名
	 * @return
	 */
	public List<BusStation> queryStationByStationName(String name,boolean contaionBM,int limit)
	{
		String hql="from BusStation b where b.Name like '%"+name+"%'";
		
		if(contaionBM)
			hql+=" or b.Rename like '%"+name+"%'";
		
		return (List<BusStation>) this.hibernateTemplate.find(hql);
	}
	
	/**
	 * 站点默认查询(不匹配别名)
	 * @param name 站名
	 * @return
	 */
	public List<BusStation> queryStationByStationName(String name,int limit)
	{
		return queryStationByStationName(name, false,limit);
	}

	/**
	 * 
	 * @return
	 */
	public List<?> queryAllLines()
	{
		return querySQLForMap("select * from z_gj_xljbxx");
	}
	
	/**
	 * 
	 * @return
	 */
	public List<?> queryAllStations()
	{
		return querySQLForMap("select * from z_gj_zdjbxx");
	}
	
	/**
	 * 
	 * @return
	 */
	public List<?> queryAllBikes()
	{
		return querySQLForMap("select * from z_zxc_zdxx");
	}
	
	protected List<?> querySQLForMap(String sql) {
		Session session = null;
		List<?> list = null;
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createSQLQuery(sql);
			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
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

	/**
	 *  删除所有自行车站点信息
	 */
	public void ClearZdxx()
	{
		final String hql = "delete from ZxcZdxx";
		Session session = null;
		try
		{
			session = this.hibernateTemplate.getSessionFactory().openSession();
			Transaction trans = session.beginTransaction();
			session.createQuery(hql).executeUpdate();
			trans.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(session != null)
			{
				session.clear();
				session.close();
			}
		}
	}
	
	/**
	 * 保存自行车站点信息
	 * 
	 * @param zdxx
	 */
	public void saveZdxx(ZxcZdxx zdxx)
	{
		this.hibernateTemplate.save(zdxx);
	}

}
