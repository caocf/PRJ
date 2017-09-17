package com.sts.parkingdb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 * 查询
 * 
 * @author Administrator
 * 
 */
public class ParkingDBDao {
	HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 根据ID搜索
	 * 
	 * @param parkingID
	 *            停车次id
	 * @param isReal
	 *            是否包含实时信息
	 * @return
	 */
	public List<?> searchParkingByID(String parkingID, boolean isReal) {

		String hql = "";

		if (!isReal) {
			hql = "from ParkingDB p where p.parkpointid='" + parkingID + "'";
		} else {
			hql = "select p,r from ParkingDB as p,ParkingDBReal as r where p.parkpointid='"
					+ parkingID
					+ "'"
					+ " and r.parkpointid='"
					+ parkingID
					+ "'";
		}

		return this.hibernateTemplate.find(hql);
	}

	/**
	 * 根据类型和名称获取
	 * 
	 * @param type
	 *            -1为全部类型
	 * @param name
	 *            ""为全部名称
	 * @param isReal
	 *            是否实时
	 * @return
	 */
	public List<?> searchParkingByNameAndType(int type, String name, int page,
			int num, boolean isReal) {

		String hql = "";

		if (isReal) {
			if (type == -1 && name.equals(""))
				hql = "select p,r from ParkingDB as p,ParkingDBReal as r where p.parkpointid=r.parkpointid";
			else if (type != -1 && name.equals("")) {
				hql = "select p,r from ParkingDB as p,ParkingDBReal as r where p.parktype="
						+ type + " and p.parkpointid=r.parkpointid";
			} else if (type == -1 && !name.equals("")) {
				hql = "select p,r from ParkingDB as p,ParkingDBReal as r where p.pointname like '%"
						+ name + "%' and p.parkpointid=r.parkpointid";
			} else {
				hql = "select p,r from ParkingDB as p,ParkingDBReal as r where p.pointname like '%"
						+ name
						+ "%' and p.parktype="
						+ type
						+ " and p.parkpointid=r.parkpointid";
			}
		} else {
			if (type == -1 && name.equals(""))
				hql = "select p from ParkingDB as p";
			else if (type != -1 && name.equals("")) {
				hql = "select p from ParkingDB as p where p.parktype=" + type;
			} else if (type == -1 && !name.equals("")) {
				hql = "select p from ParkingDB as p where p.pointname like '%"
						+ name + "%'";
			} else {
				hql = "select p from ParkingDB as p where p.pointname like '%"
						+ name + "%' and p.parktype=" + type;
			}
		}

		System.out.println(hql);
		if (page > 0 && num > 0)
			return queryCount(hql, (page - 1) * num, num);
		else
			return this.hibernateTemplate.find(hql);
	}

	/**
	 * 根据区域查询
	 * 
	 * @param lan1
	 * @param lon1
	 * @param lan2
	 * @param lon2
	 * @param page
	 * @param num
	 * @param isReal
	 * @return
	 */
	public List<?> searchParkingByCenterAndRadius(double lan1, double lon1,
			double lan2, double lon2, int page, int num, boolean isReal) {
		String hql = "";
		if (isReal) {
			hql = "select p,r from ParkingDB as p,ParkingDBReal as r where (p.gpsla between "
					+ lan1
					+ " and "
					+ lan2
					+ ") and (p.gpslo between "
					+ lon1
					+ " and " + lon2 + ") and p.parkpointid=r.parkpointid";
		} else {
			hql = "select p from ParkingDB as p where (p.gpsla between " + lan1
					+ " and " + lan2 + ") and (p.gpslo between " + lon1
					+ " and " + lon2 + ")";
		}

		System.out.println(hql);

		if (page > 0 && num > 0)
			return queryCount(hql, (page - 1) * num, num);
		else
			return this.hibernateTemplate.find(hql);
	}

	// 分页
	protected List<?> queryCount(String hql, int startSet, int maxSet) {
		Session session = null;
		List<?> list = null;
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createQuery(hql);
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

	// 总数
	public int countRecord(String hql) {
		Query query = this.hibernateTemplate.getSessionFactory().openSession()
				.createQuery(hql);

		return ((Number) query.uniqueResult()).intValue();
	}
}
