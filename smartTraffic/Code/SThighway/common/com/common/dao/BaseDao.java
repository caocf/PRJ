package com.common.dao;

/**
 * BaseDao定义一些常用的接口，此类接口不应与数据持久层是何种而不同
 * 
 * @author DongJun
 * 
 * @param <E>
 */
public interface BaseDao<E> {
	/**
	 * 保存对象
	 */
	public void save(E o);

	/**
	 * 删除对象
	 */
	public void delete(E o);

	/**
	 * 更新对象
	 */
	public void update(E o);

	/**
	 * 保存或更新对象
	 */
	public void saveOrUpdate(E o);

	/**
	 * 查找所有对象
	 */
	public BaseQueryRecords find(E o);

	/**
	 * 查找所有对象，并排序
	 */
	public BaseQueryRecords findOrderBy(E o, String orderby, boolean ifdesc);

	/**
	 * 查找所有对象 带分页
	 */
	public BaseQueryRecords find(E o, int page, int rows);

	/**
	 * 查找所有对象 带分页, 并排序
	 */
	public BaseQueryRecords findOrderBy(E o, String orderby, boolean ifdesc,
			int page, int rows);

	/**
	 * 查找满足某一条件的所有对象
	 */
	public BaseQueryRecords find(E o, String key, Object value);

	/**
	 * 查找满足某一条件的所有对象, 并排序
	 */
	public BaseQueryRecords findOrderBy(E o, String key, Object value,
			String orderby, boolean ifdesc);

	/**
	 * 查找满足某一条件的所有对象 带分布
	 */
	public BaseQueryRecords find(E o, String key, Object value, int page,
			int rows);

	/**
	 * 查找满足某一条件的所有对象 带分布,并排序
	 */
	public BaseQueryRecords findOrderBy(E o, String key, Object value,
			String orderby, boolean ifdesc, int page, int rows);

	/**
	 * 查找唯一对象，如果对象不存在，返回NULL
	 */
	public E findUnique(E o, String key, Object value);

	/**
	 * 获得记录数
	 */
	public Long count(E o);

	/**
	 * 获得记录数
	 */
	public Long count(E o, String key, Object value);
	
	public Object saveAndReturn(E o);
	
	/**
	 * 获得同名记录数,更新时去除自身
	 */
    public int countSameName(String key,String value,String tableName,String bhKey,Object keyValue);
    
    /**
     * 根据编号获取名称
     */
    public String selectnameByBh(String tablename,String selectKey,String bhkey,Object bhvalue);
}
