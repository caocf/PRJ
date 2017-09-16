package com.huzhouport.log.model;

import org.hibernate.Query;
import org.hibernate.Session;

public class QueryCount {
	/**
	 *获取总计入数
	 */
	public static long getQueryCount(String sql,Session session){
		//sql="from Log l,LogStyle ls where l.partOfStyle=ls.styleId";
		
		
		//sql=sql.replace("order by l.logTime desc", "");
		//System.out.println("replace sql"+sql);
		Query query = session.createQuery(appendHql(sql));
		return (Long) query.uniqueResult();
		
	}
	
	public static String appendHql(String hql){
		int index = hql.indexOf("from");
		if(index != -1){
			return "select count(*)" + hql.substring(index);
		}else{
			try {
				throw new Exception("查询语句出错");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
}
