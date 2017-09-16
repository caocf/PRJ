package com.huzhouport.user.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.user.dao.HomePageDao;
import com.huzhouport.user.model.User;

public class HomePageDaoImpl implements HomePageDao{
	
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public List<Map<String, Object>> findHomePageInfo(User user) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Session session = null;
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			session = this.hibernateTemplate.getSessionFactory().openSession();
			session.beginTransaction();
			conn = session.connection();
			cs = conn.prepareCall("{call homePage(?)}");
			cs.setInt(1, user.getUserId());
			cs.execute();
			rs = (ResultSet) cs.executeQuery();
			Map<String, Object> map = null;
			while (rs.next()) {
				map = new HashMap<String, Object>();
				map.put("leaveorot", rs.getInt("leaveorot"));
				map.put("illega", rs.getInt("illega"));
				map.put("dangerousarrivaldeclare", rs.getInt("dangerousarrivaldeclare"));
				map.put("dangerousworkdeclare", rs.getInt("dangerousworkdeclare"));
				map.put("report", rs.getInt("report"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			cs.close();		
			conn.close();
			session.clear();
			session.close();
		}
		return list;
	}

	public List<Map<String, Object>> findHomePageInfoPrompt(User user)throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Session session = null;
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			session = this.hibernateTemplate.getSessionFactory().openSession();
			session.beginTransaction();
			conn = session.connection();
			cs = conn.prepareCall("{call homePage_prompt(?)}");
			cs.setInt(1, user.getUserId());
			cs.execute();
			rs = (ResultSet) cs.executeQuery();
			Map<String, Object> map = null;
			while (rs.next()) {
				map = new HashMap<String, Object>();
				map.put("state", rs.getInt("state"));
				map.put("eventName", rs.getString("eventName"));
				map.put("eventLocation", rs.getString("eventLocation"));
				map.put("EventTime", rs.getString("EventTime"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			cs.close();		
			conn.close();
			session.clear();
			session.close();
		}
		return list;
	}

}
