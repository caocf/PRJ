package com.sts.news.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.news.model.NewsDetailForPhone;
import com.sts.news.model.NewsForPhone;
import com.sts.news.model.RoadInfo;
import com.sts.news.model.RoadWork;

public class NewsDao {

	HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 查询新闻列表
	public List<NewsForPhone> queryForPhones(int type, List<Integer> subtype,
			int page, int num) {
		String hql = "from NewsForPhone n where (n.type=" + type + ")";

		if (subtype != null && subtype.size() > 0)
			hql += " and (";

		for (int i = 0; i < subtype.size(); i++) {
			hql += " n.subType=" + subtype.get(i);

			if (i != (subtype.size() - 1))
				hql += " or ";
		}
		hql += ")";

		return (List<NewsForPhone>) queryCount(hql, (page - 1) * num, num);
	}

	// 查询新闻详情
	public List<NewsDetailForPhone> queryNewsForDetail(int id) {
		String hql = " from NewsDetailForPhone n where n.id=" + id;

		return (List<NewsDetailForPhone>) this.hibernateTemplate.find(hql);
	}

	// 获取总数
	public int count(NewsForPhone e) {
		try {
			return 0;
		} catch (Exception ex) {

		}

		return 0;
	}

	// 路况信息
	public List<RoadInfo> queryRoadInfoList(int start, int num) {
		String hql = "from RoadInfo r ";

		System.out.println(hql);

		return (List<RoadInfo>) queryCount(hql, start, num);
	}

	// 施工信息
	public List<RoadWork> queryRoadWorkInfoList(int start, int num) {
		String hql = "from RoadWork r ";

		System.out.println(hql);

		return (List<RoadWork>) queryCount(hql, start, num);
	}

	public int queryRoadCount() {
		String hql = "select count(*) from RoadInfo r";
		return countRecord(hql);
	}

	public int queryRoadWorkCount() {
		String hql = "select count(*) from RoadWork r";
		return countRecord(hql);
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
		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		int result = 0;
		try {
			Query query = session.createQuery(hql);
			result = ((Number) query.uniqueResult()).intValue();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}

		return result;
	}

}
