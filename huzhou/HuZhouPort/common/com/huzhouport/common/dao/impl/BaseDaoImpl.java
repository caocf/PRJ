package com.huzhouport.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.common.util.rmpfLog;


public class BaseDaoImpl<E> implements BaseDao<E> {

	protected HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 获取记录总数
	 */
	public int countRecord(E e) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		int count = hibernateTemplate.findByCriteria(detachedCriteria).size();
		return count;
	}

	/**
	 * 分页查询数据，beginIndex：从第几条数据开始，pageSize:取多少条数据
	 */
	@SuppressWarnings("unchecked")
	public List<E> queryByIndex(E e, int beginIndex, int pageSize)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		List<E> list = hibernateTemplate.findByCriteria(detachedCriteria,
				beginIndex, pageSize);
		return list;
	}

	@rmpfLog(desc = "删除")
	public void delete(E e) throws Exception {
		try {
			this.hibernateTemplate.delete(e);
		} catch (DataIntegrityViolationException e1) {
			throw new DataIntegrityViolationException("该信息已被引用，请先删除引用");
		}

	}

	@rmpfLog(desc = "增加")
	public void save(E e) {
		try {
			this.hibernateTemplate.save(e);
		} catch (Exception e1) {
		}

	}

	@rmpfLog(desc = "修改")
	public void update(E e) {
		try {
			this.hibernateTemplate.update(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 外键选择数据（主要用于分页查询）
	 * 
	 * @param e
	 *            对象名称
	 * @param hql
	 *            hql 语句
	 * @param startSet
	 *            从第几条开始
	 * @param maxSet取出几条
	 * @return
	 */
	@SuppressWarnings( { "unchecked" })
	protected List<?> queryqueryEForeignKey(Object e, String hql, int startSet,
			int maxSet) {
		Session session = null;
		List<?> list = new ArrayList();
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
	//重载一个时间排序的方法
	@SuppressWarnings( { "unchecked" })
	protected List<?> queryqueryEForeignKeyTime(Object e, String hql, int startSet,
			int maxSet) {
		Session session = null;
		List<?> list = new ArrayList();
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

	/**
	 * 外键查询总数
	 * 
	 * @param e
	 *            对象名称
	 * @param hql
	 *            查询语句
	 * @return
	 * @throws Exception
	 */
	public int countSqlEForeignKey(E e, String sql) throws Exception {
		int count = 0;
		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		try {
			session.beginTransaction();
			Query q = session.createSQLQuery(sql);
			count = Integer.parseInt(q.uniqueResult().toString());
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	protected List<?> sqlQueryEForeignKey(Object e, String sql, int startSet,
			int maxSet) {
		Session session = null;
		List<?> list = new ArrayList();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createSQLQuery(sql);
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
	@SuppressWarnings("unchecked")
	protected List<?> sqlForeignKey( String sql) {
		Session session = null;
		List<?> list = new ArrayList();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createSQLQuery(sql);
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
	 * 外键选择数据（一般查询）
	 * 
	 * @param obj对象名称
	 * @param hql
	 *            hql语句
	 * @return
	 */
	protected List<?> queryqueryEForeignById(Object obj, String hql) {

		Session session = null;
		List<?> list = null;
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

	/**
	 * 外键查询总数
	 * 
	 * @param e
	 *            对象名称
	 * @param hql
	 *            查询语句
	 * @return
	 * @throws Exception
	 */
	public int countEForeignKey(E e, String hql) throws Exception {
		int count = 0;
		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		try {
			session.beginTransaction();
			Query q = session.createQuery(hql);
			count = Integer.parseInt(q.uniqueResult().toString());
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}
		return count;
	}

	/**
	 *主要用于 外键计算sum
	 * 
	 * @param hql
	 *            查询语句
	 * @return 返回Double类型
	 * @throws Exception
	 */
	public Double DoubleValue(String hql) throws Exception {
		System.out.println(hql);
		Double count = 0.0;
		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		try {
			session.beginTransaction();
			Query q = null;
			q = session.createQuery(hql);

			if (q.uniqueResult() != null) {
				count = Double.valueOf(q.uniqueResult().toString());
			}
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}
		return count;
	}

	/**
	 * 查询表中所有的数据
	 */
	@SuppressWarnings("unchecked")
	public List<E> queryAllData(E e) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		List<E> list = new ArrayList<E>();
		try {
			list = this.hibernateTemplate.findByCriteria(detachedCriteria);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}

	/**
	 * 按条件查询表里面数据(无分页)、fieldName：字段名称，value：字段名称对应的值
	 */
	@SuppressWarnings("unchecked")
	public List<E> queryDataByConditions(E e, String fieldName, Object value)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		detachedCriteria.add(Restrictions.eq(fieldName, value));
		List<E> list = null;
		try {
			list = this.hibernateTemplate.findByCriteria(detachedCriteria);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}

	/**
	 * 按条件查询条数、fieldName：字段名称，value：字段名称对应的值
	 */
	public int countDataByConditionsPaging(E e, String fieldName, String value)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		detachedCriteria.add(Restrictions.eq(fieldName, value));
		int count = hibernateTemplate.findByCriteria(detachedCriteria).size();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<E> queryDataByConditionsPaging(E e, String fieldName,
			String value, int startSet, int maxSet) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		detachedCriteria.add(Restrictions.eq(fieldName, value));
		List<E> list = null;
		list = this.hibernateTemplate.findByCriteria(detachedCriteria,
				startSet, maxSet);
		return list;
	}

	/**
	 * 一个字段多条件查询 e是实体名字， fieldName字段 list 是要查的条件值
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<E> queryDataByManyConditions(String e, String fieldName,
			String[] list) {
		String hql = null;
		for (int i = 0; i < list.length; i++) {
			if (i == 0) {
				hql = "'" + list[0] + "',";
			} else if (i == list.length - 1) {
				hql += "'" + list[i] + "'";
			} else {
				hql += "'" + list[i] + "',";
			}
		}
		String sql = "from " + e + " a where a." + fieldName + " in(" + hql
				+ ")";
		System.out.println(sql);
		List<E> listData = null;
		listData = this.hibernateTemplate.find(sql);
		// list=this.hibernateTemplate.findByCriteria(detachedCriteria);
		return listData;
	}

	/**
	 * 分页排序 conditionMap:key条件字段，value条件值 SortField虚按此字段排序
	 * orderingRules排序方式，升序或者降序 startSet取数据从某行起 maxSet:最多去几行
	 */
	@SuppressWarnings("unchecked")
	public List<E> queryDataPaginationAndSorting(E e,
			Map<String, String> conditionMap, String SortField,
			String orderingRules, int startSet, int maxSet) throws Exception {
		String hql = null;
		String sqlCondition = "";
		String isOrder = "";
		if (conditionMap != null) {
			sqlCondition = " where ";
			for (int i = 0; i < conditionMap.size(); i++) {
				String fieldName = (String) conditionMap.keySet().toArray()[i];
				String value = (String) conditionMap.values().toArray()[i];
				if (value.split("_")[0].equals("date")) {
					value = "=to_date('" + value.split("_")[1]
							+ "','yyyy-mm-dd')";
				} else {
					value = "='" + value + "'";
				}
				if (i == 0) {
					sqlCondition += fieldName + value;
				} else {
					sqlCondition += " and " + fieldName + value;
				}
			}
		}
		if (SortField != null || SortField != "" || orderingRules != ""
				|| orderingRules != null) {
			isOrder = " order by " + SortField + " " + orderingRules;
		}
		hql = "from " + e.getClass().getName() + sqlCondition + isOrder;
		return (List<E>) queryqueryEForeignKey(e, hql, startSet, maxSet);
	}

	/**
	 * 按条件查询条数（多条件） conditionMap 封装条件 key是字段，value 是对应的值
	 */
	public int countDataByConditionsFuzzy(E e, Map<String, String> conditionMap)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		if (conditionMap != null) {
			for (int i = 0; i < conditionMap.size(); i++) {
				String fieldName = (String) conditionMap.keySet().toArray()[i];
				String value = (String) conditionMap.values().toArray()[i];
				detachedCriteria.add(Restrictions.like(fieldName, "%" + value
						+ "%"));
			}
			int count = hibernateTemplate.findByCriteria(detachedCriteria)
					.size();
			return count;
		}
		return 0;
	}

	/**
	 * 分页按条件查询数据（多条件）conditionMap 封装条件 key是字段，value 是对应的值
	 * ，SortField：排序字段，orderingRules：排序规则，startSet：从第几条取数据，maxSet：取多少条
	 */
	@SuppressWarnings("unchecked")
	public List<E> SearchDataPaginationAndSortingAndFuzzy(E e,
			Map<String, String> conditionMap, String SortField,
			String orderingRules, int startSet, int maxSet) throws Exception {
		String hql = null;
		String sqlCondition = "";
		String isOrder = "";
		if (conditionMap != null) {
			sqlCondition = " where ";
			for (int i = 0; i < conditionMap.size(); i++) {
				String fieldName = (String) conditionMap.keySet().toArray()[i];
				String value = (String) conditionMap.values().toArray()[i];
				if (value.split("_")[0] == "date") {
					value = "like to_date('" + value.split("_")[1]
							+ "','yyyy-mm-dd')";
				} else {
					value = " like '%" + value + "%'";
				}
				if (i == 0) {
					sqlCondition += fieldName + value;
				} else {
					sqlCondition += " and " + fieldName + value;
				}
			}
		}
		if (!SortField.equals(null) && !SortField.equals("")
				&& !orderingRules.equals("") && !orderingRules.equals(null)) {
			isOrder = " order by " + SortField + " " + orderingRules;
		}
		hql = "from " + e.getClass().getName() + sqlCondition + isOrder;
		return (List<E>) queryqueryEForeignKey(e, hql, startSet, maxSet);
	}

	/**
	 * 根据条件删除
	 * 
	 * @param e
	 * @param hql
	 *            hql语句
	 * @return
	 */
	@rmpfLog(desc = "删除")
	protected boolean deleteData(Object e, String hql) {
		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		try {
			session.beginTransaction();
			Query queryupdate = session.createQuery(hql);
			queryupdate.executeUpdate();
			session.getTransaction().commit();
			return true;
		} finally {
			session.close();
		}

	}

	/**
	 * 封装查询条件（模糊查询）
	 * 
	 * @param conditionMap查询条件
	 *            key:查询字段，value：查询字段对应的值
	 * @return
	 */
	protected String fuzzyCondition(Map<String, String> conditionMap) {
		String sqlCondition = "";
		for (int i = 0; i < conditionMap.size(); i++) {
			String fieldName = (String) conditionMap.keySet().toArray()[i];
			String value = (String) conditionMap.values().toArray()[i];
			if (value.split("_")[0] == "date") {
				value = "= to_date('" + value.split("_")[1] + "','yyyy-mm-dd')";
			} else {
				value = " like '%" + value + "%'";
			}
			if (i == 0) {
				sqlCondition += " and " + fieldName + value;
			} else {
				sqlCondition += " and " + fieldName + value;
			}
		}
		return sqlCondition;
	}

	/**
	 * 封装查询条件（不是模糊查询）
	 * 
	 * @param conditionMap查询条件
	 *            key:查询字段，value：查询字段对应的值
	 * @return
	 */
	protected String isNotfuzzyCondition(Map<String, String> conditionMap) {
		String sqlCondition = "";
		for (int i = 0; i < conditionMap.size(); i++) {
			String fieldName = (String) conditionMap.keySet().toArray()[i];
			String value = (String) conditionMap.values().toArray()[i];
			if (value.split("_")[0].equals("date")) {
				value = "=to_date('" + value.split("_")[1] + "','yyyy-mm-dd')";
			} else {
				value = "='" + value + "'";
			}
			if (i == 0) {
				sqlCondition += " and " + fieldName + value;
			} else {
				sqlCondition += " and " + fieldName + value;
			}
		}
		return sqlCondition;
	}

	/**
	 * 按条件查询条数（多条件）conditionMap的 key:查询字段，value：查询字段对应的值
	 */
	public int countDataByConditions(E e, Map<String, String> conditionMap)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		if (conditionMap != null) {
			for (int i = 0; i < conditionMap.size(); i++) {
				String fieldName = (String) conditionMap.keySet().toArray()[i];
				String value = (String) conditionMap.values().toArray()[i];
				detachedCriteria.add(Restrictions.eq(fieldName, value));
			}
			int count = hibernateTemplate.findByCriteria(detachedCriteria)
					.size();
			return count;
		}
		return 0;
	}

	/**
	 * 修改 modifyMap修改字段的map，其中map中的key:修改字段，value:修改值 ；conditionMap
	 * 条件map,其中，key:条件字段，value：条件值  conditionPair：条件 or 还是and
	 */
	@SuppressWarnings("finally")
	public boolean modifyByConditions(Object e, Map<String, String> modifyMap,
			Map<String, String> conditionMap, String conditionPair)
			throws Exception {
		String hql = "update " + e.getClass().getSimpleName() + " as e set ";
		String condition = "";
		for (int i = 0; i < modifyMap.size(); i++) {
			String fieldName = (String) modifyMap.keySet().toArray()[i];
			String value = (String) modifyMap.values().toArray()[i];
			if (i == 0) {
				hql += "e." + fieldName + "='" + value + "'";
			} else {
				hql += ",e." + fieldName + "='" + value + "'";
			}
		}
		if (conditionMap != null) {
			condition = " where ";
			for (int i = 0; i < conditionMap.size(); i++) {
				String fieldName = (String) conditionMap.keySet().toArray()[i];
				String value = (String) conditionMap.values().toArray()[i];

				if (i == 0) {
					condition += "e." + fieldName + "='" + value + "'";
				} else {
					if (conditionPair != null) {
						condition += conditionPair + " e." + fieldName + "='"
								+ value + "'";
					}
				}
			}
		}
		try {
			this.hibernateTemplate.bulkUpdate(hql + condition);
			return true;
		} catch (Exception e1) {
			return false;
		} finally {
			return false;
		}
	}
	/**
	 * 修改 modifyMap修改字段的map，其中map中的key:修改字段，value:修改值 ；
	 */
	@SuppressWarnings("finally")
	public boolean updateByInt(Object e, Map<String, Integer> modifyMap,
			Map<String, Integer> conditionMap, String conditionPair)
			throws Exception {
		String hql = "update " + e.getClass().getSimpleName() + " as e set ";
		String condition = "";
		for (int i = 0; i < modifyMap.size(); i++) {
			String fieldName = (String) modifyMap.keySet().toArray()[i];
			Object value = modifyMap.values().toArray()[i];
			if (i == 0) {
				hql += "e." + fieldName + "='" + value + "'";
			} else {
				hql += ",e." + fieldName + "='" + value + "'";
			}
		}
		if (conditionMap != null) {
			condition = " where ";
			for (int i = 0; i < conditionMap.size(); i++) {
				String fieldName = (String) conditionMap.keySet().toArray()[i];
				Object value =  conditionMap.values().toArray()[i];

				if (i == 0) {
					condition += "e." + fieldName + "='" + value + "'";
				} else {
					if (conditionPair != null) {
						condition += conditionPair + " e." + fieldName + "='"
								+ value + "'";
					}
				}
			}
		}
		try {
			this.hibernateTemplate.bulkUpdate(hql+ condition);
			return true;
		} catch (Exception e1) {
			return false;
		} finally {
			return false;
		}
	}

	/**
	 * 保存或修改
	 */
	public boolean saveorUpdate(E e) throws Exception {
		try {
			this.hibernateTemplate.saveOrUpdate(e);
			return true;
		} catch (Exception e1) {
			throw new Exception("有误" + e1.getMessage());
		}
	}

	/**
	 * 按条件查询 e:对象名称，fieldName字段 list 是要查的条件值
	 */
	public List<?> queryDataByConditionsObject(Object e, String fieldName,
			Object value) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(e
				.getClass());
		detachedCriteria.add(Restrictions.eq(fieldName, value));
		List<?> list = null;
		try {
			list = this.hibernateTemplate.findByCriteria(detachedCriteria);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}
    /**
     * 刷新
   
	public Object getRefresh(Object obj) {
		this.hibernateTemplate.refresh(obj);
		return obj;
	}  */
	public void savaObject(Object e) throws Exception {

		this.hibernateTemplate.save(e);

	}

	public void deleteObject(Object e) throws Exception {
		this.hibernateTemplate.delete(e);
		
	}
}
