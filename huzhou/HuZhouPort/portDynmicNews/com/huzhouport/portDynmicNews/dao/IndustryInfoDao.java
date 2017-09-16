package com.huzhouport.portDynmicNews.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.portDynmicNews.model.IndustryInfo;

@SuppressWarnings("all")
public class IndustryInfoDao extends BaseDaoImpl<IndustryInfo> {
	public IndustryInfo addIndustryInfo(IndustryInfo info) {
		this.save(info);
		return info;
	}

	public void updateIndustryInfo(IndustryInfo info) {
		this.update(info);
	}

	public void deleteIndustryInfo(int id) {
		IndustryInfo info = this.findIndustryInfoDetail(id);
		if (info != null)
			try {
				this.delete(info);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public BaseQueryRecords findIndustryInfoList(int page, int rows) {
		BaseQueryRecords records = new BaseQueryRecords();
		Session session = null;
		List<IndustryInfo> list = new ArrayList<IndustryInfo>();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query q = session
					.createQuery("from IndustryInfo i ORDER BY i.updatetime desc");

			int total = q.list().size();
			records.setTotal(total);
			if (page != -1 && rows != -1) {
				int pages = total / rows;
				if (total % rows != 0) {
					pages++;
				}
				records.setPages(pages);
				q.setFirstResult((page - 1) * rows); // 从第几条开始
				q.setMaxResults(rows); // 取出几条
			}
			list = q.list();
			session.getTransaction().commit();

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		records.setList(list);
		return records;
	}

	public BaseQueryRecords searchIndustryInfoList(String searchStr, int page,
			int rows) {
		BaseQueryRecords records = new BaseQueryRecords();
		Session session = null;
		List<IndustryInfo> list = new ArrayList<IndustryInfo>();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query q = session
					.createQuery("from IndustryInfo i where i.title like '%"
							+ searchStr + "%' or i.content like '%" + searchStr
							+ "%' ORDER BY i.updatetime desc");

			int total = q.list().size();
			records.setTotal(total);
			if (page != -1 && rows != -1) {
				int pages = total / rows;
				if (total % rows != 0) {
					pages++;
				}
				records.setPages(pages);
				q.setFirstResult((page - 1) * rows); // 从第几条开始
				q.setMaxResults(rows); // 取出几条
			}
			list = q.list();
			session.getTransaction().commit();

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		records.setList(list);
		return records;
	}

	public BaseQueryRecords findInnerIndustryInfoList(int page, int rows) {
		BaseQueryRecords records = new BaseQueryRecords();
		Session session = null;
		List<IndustryInfo> list = new ArrayList<IndustryInfo>();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query q = session
					.createQuery("from IndustryInfo i where i.oriobj !="
							+ IndustryInfo.oriobj_public
							+ " ORDER BY i.updatetime desc");

			int total = q.list().size();
			records.setTotal(total);
			if (page != -1 && rows != -1) {
				int pages = total / rows;
				if (total % rows != 0) {
					pages++;
				}
				records.setPages(pages);
				q.setFirstResult((page - 1) * rows); // 从第几条开始
				q.setMaxResults(rows); // 取出几条
			}
			list = q.list();
			session.getTransaction().commit();

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		records.setList(list);
		return records;

	}

	public BaseQueryRecords findPublicIndustryInfoList(int page, int rows) {
		BaseQueryRecords records = new BaseQueryRecords();
		Session session = null;
		List<IndustryInfo> list = new ArrayList<IndustryInfo>();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query q = session
					.createQuery("from IndustryInfo i where i.oriobj !="
							+ IndustryInfo.oriobj_inner
							+ " ORDER BY i.updatetime desc");

			int total = q.list().size();
			records.setTotal(total);
			if (page != -1 && rows != -1) {
				int pages = total / rows;
				if (total % rows != 0) {
					pages++;
				}
				records.setPages(pages);
				q.setFirstResult((page - 1) * rows); // 从第几条开始
				q.setMaxResults(rows); // 取出几条
			}
			list = q.list();
			session.getTransaction().commit();

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		records.setList(list);
		return records;
	}

	public IndustryInfo findIndustryInfoDetail(int id) {
		Session session = null;
		List<IndustryInfo> list = new ArrayList<IndustryInfo>();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query q = session.createQuery("from IndustryInfo i where i.id="
					+ id);

			list = q.list();
			session.getTransaction().commit();

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
