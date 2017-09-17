package com.sts.smartbus.download.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.smartbus.download.dao.DownloadDao;
import com.sts.smartbus.download.model.Download;

public class DownloadDaoImpl implements DownloadDao {
	HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 添加下载记录
	public void addDownload(Download download) {
		hibernateTemplate.save(download);
	}

	public List<Map<String, Object>> queryDownloadCount(String start,String end) {

		String hql = "select d.appVersion,COUNT(*) from Download d ";

		if(start!=null &&!start.equals(""))
		{
			hql+=" where d.date >='"+start+"'";
			
			if(end!=null &&!end.equals(""))
			{
				hql+=" and d.date <='"+end+"'";
			}
		}
		else if(end!=null &&!end.equals(""))
		{
			hql+=" where d.date <='"+end+"'";
		}
		
		hql+= " GROUP BY d.appVersion ";
		
		return countByGroup(hql);
	}

	// 分组统计（如以后常用可修改加到basedao中）
	public List<Map<String, Object>> countByGroup(String hql) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		try {
			Transaction tx = session.beginTransaction();
			Iterator it = session.createQuery(hql).iterate();

			while (it.hasNext()) {
				Map<String, Object> temp = new HashMap<String, Object>();

				Object[] oc = (Object[]) it.next();

				temp.put("versionId", oc[0].toString());
				temp.put("count", oc[1].toString());

				result.add(temp);
			}
			tx.commit();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			session.clear();
			session.close();
		}

		return result;
	}
}
