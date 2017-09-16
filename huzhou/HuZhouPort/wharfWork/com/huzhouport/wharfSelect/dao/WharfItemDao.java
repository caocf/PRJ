package com.huzhouport.wharfSelect.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.wharfSelect.model.WharfItem;

public class WharfItemDao extends BaseDaoImpl<WharfItem>{
	/**
	 * 搜索
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WharfItem> searchWharfItems(int portareaid, int pieceareaid,
			String wharfname) {
		String hql = "select wh from WharfItem wh, PieceArea pi, PortArea po"
			+" where wh.pieceareaid = pi.id and pi.portareaid = po.id";
		
		if (portareaid > 0){
			hql += " and po.id="+portareaid;
		}
		if (pieceareaid > 0) {
			hql += " and pi.id="+pieceareaid;
		}
		if (wharfname != null && !wharfname.equals("")) {
			hql += " and wh.mc like '%"+wharfname+"%'";
		}
		
		Session session = null;
		List<WharfItem> list = new ArrayList<WharfItem>();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createQuery(hql);
			list = q.list();
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}
		return list;
	}
	
	public WharfItem findByBH(String bh) {
		WharfItem item = null;
		try {
			List<WharfItem> lists = this.queryDataByConditions(new WharfItem(), "bh", bh);
			if (lists != null && lists.size() > 0)
				item = lists.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}
}
