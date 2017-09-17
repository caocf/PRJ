package com.edb.edit.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.edb.edit.model.ColumnComment;
import com.edb.edit.model.ColumnType;
import com.edb.edit.model.KeyValue;
import com.edb.edit.model.Table;
import com.edb.edit.model.UserTablePrivs;
import com.edb.hibernate.SessionFactoryManager;

public class EditDao {

	public String getUserName() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		return (String) session.getAttribute("username");
	}

	public String getPassword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		return (String) session.getAttribute("password");
	}

	@SuppressWarnings("unchecked")
	public List<Table> queryAllTables() {
		SessionFactory sessionFactory = SessionFactoryManager
				.getSessionFactory(getUserName(), getPassword());
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		try {
			String sql = "select t.owner,t.table_name,t.comments from all_tab_comments t where t.owner = 'ZHJTADMIN'";

			Query query = session.createSQLQuery(sql);
			List<?> list = query.list();

			List<Table> listTable = new ArrayList<Table>();
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				Table tb = new Table();
				tb.setName((String)objs[1]);
				tb.setOwner((String)objs[0]);
				tb.setComment((String)objs[2]);
				listTable.add(tb);
			}
			return listTable;
			
		} catch (Exception e) {
			tr.rollback();
			System.out.println(e.toString());
		} finally {
			session.clear();
			session.close();
		}

		return null;
	}

	public List<?> queryColumnByTableName(String name) {
		String hql = "select t.owner,t.table_name,t.column_name,t.data_type,t.data_length,c.owner,c.table_name,c.column_name,c.comments,t.nullable from all_tab_columns t,all_col_comments c where c.owner=t.owner and t.table_name=c.table_name and c.column_name=t.column_name and  c.owner='ZHJTADMIN' and t.table_name='"
				+ name + "' order by t.column_id ";

		try
		{
			List<?> temp=querySQL(hql);
			
			List<Object[]> result=new ArrayList<Object[]>();
			
			for(int i=0;i<temp.size();i++)
			{
				Object[] objs=(Object[])temp.get(i);
				
				Object[] oneObjects=new Object[2];

				ColumnType column=new ColumnType();
				column.setOwner((String)objs[0]);
				column.setTableName((String)objs[1]);
				column.setColumnName((String)objs[2]);
				column.setColumnType((String)objs[3]);
				column.setNullable((String)objs[9]);
				
				BigDecimal bigDecimal=new BigDecimal(String.valueOf(objs[4]));
				
				column.setColumnLength(bigDecimal.intValue());
				
				oneObjects[0]=column;
				
				ColumnComment comment=new ColumnComment();
				comment.setOwner((String)objs[5]);
				comment.setTableName((String)objs[6]);
				comment.setColumnName((String)objs[7]);
				comment.setColumnComment((String)objs[8]);
				
				oneObjects[1]=comment;
				
				result.add(oneObjects);
			}
			
			return result;
		}
		catch (Exception e) {

		}
		
		return null;
	}

	public int count(String tableName) {
		int result = 0;

		String hql = "select count(*) from ZHJTADMIN." + tableName;
		List<?> r = querySQL(hql);

		result = Integer.valueOf(r.get(0).toString());

		return result;
	}

	public List<?> queryContentByTabeName(String name, int page, int num) {
		// String hql = "select t.*,t.rowid from " + name + " t";

		String hql = "select b.* from (select t.* ,t.rowid id,rownum row_num from "
				+ " ZHJTADMIN."
				+ name
				+ " t) b where b.row_num between "
				+ (page - 1) * num + " and " + page * num;

		return querySQL(hql);
	}

	public List<?> queryOneRow(String name, String rowid) {
		String hql = "select t.*,t.rowid from ZHJTADMIN." + name
				+ " t where t.rowid='" + rowid + "'";

		return querySQL(hql);
	}


	public Map<String, Integer> queryType(String name) {
		List<Object[]> r = (List<Object[]>) queryColumnByTableName(name);

		Map<String, Integer> result = new HashMap<String, Integer>();

		for (Object[] o : r) {
			ColumnType type = (ColumnType) o[0];

			if (type.getColumnType().equals("NUMBER")) {
				result.put(type.getColumnName(), 1);
			} else if (type.getColumnType().equals("VARCHAR2")) {
				result.put(type.getColumnName(), 2);
			} else if (type.getColumnType().equals("DATE")) {
				result.put(type.getColumnName(), 3);
			}
		}

		return result;

	}

	public String addContent(String table, List<KeyValue> colum) {

		Map<String, Integer> type = queryType(table);

		String hql = "insert into ZHJTADMIN." + table + " (";

		for (KeyValue e : colum) {
			hql += e.getKey() + ",";
		}

		hql = hql.substring(0, hql.length() - 1);
		hql += " ) VALUES (";

		for (KeyValue e : colum) {

			if (type.get(e.getKey()) == 2)
				hql += "'" + e.getValue() + "',";
			else if (type.get(e.getKey()) == 3) {
				hql += " to_date ('" + e.getValue()
						+ "','YYYY-MM-DD HH24:MI:SS'),";
			} else
				hql += (e.getValue().equals("") ? 0 : e.getValue()) + ",";
		}
		hql = hql.substring(0, hql.length() - 1);
		hql += ")";

		System.out.println(hql);

		return editSQL(hql);
	}

	public String updateContent(String table, List<KeyValue> colum, String rowid) {
		Map<String, Integer> type = queryType(table);

		String hql = "update ZHJTADMIN." + table + " t set ";

		for (KeyValue e : colum) {

			hql += "t." + e.getKey() + "=";

			if (type.get(e.getKey()) == 2)
				hql += "'" + e.getValue() + "',";
			else if (type.get(e.getKey()) == 3) {
				hql += " to_date ('" + e.getValue()
						+ "','YYYY-MM-DD HH24:MI:SS'),";
			} else
				hql += (e.getValue().equals("") ? 0 : e.getValue()) + ",";
		}
		hql = hql.substring(0, hql.length() - 1);

		hql += " where t.rowid='" + rowid + "'";

		System.out.println(hql);

		return editSQL(hql);
	}

	public String delete(String table, String rowid) {

		String hql = "delete from ZHJTADMIN." + table + " t where t.rowid='"
				+ rowid + "'";

		return editSQL(hql);

	}


	public String editSQL(String hql) {

		String result = "success";

		Session session = SessionFactoryManager.getSessionFactory(
				getUserName(), getPassword()).openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.createSQLQuery(hql).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
			result = e.toString();
			return result;
		} finally {
			session.clear();
			session.close();
		}

		return result;
	}


	public List<?> querySQL(String hql) {
		List<?> result = null;

		Session session = SessionFactoryManager.getSessionFactory(
				getUserName(), getPassword()).openSession();
		Transaction tx = session.beginTransaction();
				
		try {
			result = session.createSQLQuery(hql).list();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		} finally {
			session.clear();
			session.close();
		}

		return result;
	}
	public List<?> queryUserTablePrivsByTabeName(String loginName,String name) throws Exception {
		String hql = "select up.GRANTEE,up.TABLE_NAME,up.PRIVILEGE from USER_TAB_PRIVS up where up.GRANTEE='"+loginName+"' and up.TABLE_NAME='"+ name + "'";

		SessionFactory sessionFactory = SessionFactoryManager
		.getSessionFactory(getUserName(), getPassword());
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		try {

			Query query = session.createSQLQuery(hql);
			List<?> list = query.list();

			List<UserTablePrivs> listTable = new ArrayList<UserTablePrivs>();
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				UserTablePrivs tb = new UserTablePrivs();
				tb.setTableName((String)objs[1]);
				tb.setGrantee((String)objs[0]);
				tb.setPrivailege((String)objs[2]);
				listTable.add(tb);
			}
			return listTable;
			
		} catch (Exception e) {
			tr.rollback();
			System.out.println(e.toString());
		} finally {
			session.clear();
			session.close();
		}

		return null;
	}
}
