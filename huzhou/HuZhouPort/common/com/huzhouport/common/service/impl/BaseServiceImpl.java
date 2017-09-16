package com.huzhouport.common.service.impl;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.common.service.BaseService;



public class BaseServiceImpl<E> implements BaseService<E> {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 返回指定索引段的记录
	 * 
	 * @param total
	 *            查询得到的数据总条数
	 * @param maxSet
	 *            页面显示最大数据条数
	 * @param pageSet
	 *            当前页码数
	 */
	@SuppressWarnings("unchecked")
	public List<E> queryByIndex(E e, int pageNo, int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize; // 求出当前初始页最小索引值
		return baseDao.queryByIndex(e, beginIndex, pageSize);
	}

	/**
	 * @param total
	 *            记录总数
	 * @param pageSize
	 *            页面尺寸
	 * @return pages 返回总页数
	 */
	protected int calculateTotalPage(int totalRecord, int pageSize)
			throws Exception {
		int pages = 0; // 默认页数为0
		int i = totalRecord % pageSize;
		if (i == 0) {
			pages = totalRecord / pageSize;
		} else {
			pages = (totalRecord / pageSize) + 1;
		}
		return pages;
	}

	/**
	 * 计算指定表的总页数
	 */
	@SuppressWarnings("unchecked")
	public int queryTotalPage(E e, int pageSize) throws Exception {
		int total = baseDao.countRecord(e); // 获取总数据条数
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		return pages;
	}

	/***
	 * 增加
	 */
	@SuppressWarnings("unchecked")
	public boolean add(E e) throws Exception {
		this.baseDao.save(e);
		return true;
	}

	/**
	 * 删除
	 */
	@SuppressWarnings("unchecked")
	public boolean delete(E e) throws Exception {
		this.baseDao.delete(e);
		return true;
	}

	/**
	 * 修改
	 */
	@SuppressWarnings("unchecked")
	public boolean modify(E e) throws Exception {
		this.baseDao.update(e);
		return true;
	}

	/**
	 * 查询全部
	 */
	@SuppressWarnings("unchecked")
	public List<E> searchAllData(E e) throws Exception {
		return this.baseDao.queryAllData(e);
	}

	@SuppressWarnings("unchecked")
	public int countDataByConditionsPaging(E e, String fieldName, String value,
			int pageSize) throws Exception {
		int total = baseDao.countDataByConditionsPaging(e, fieldName, value); // 获取总数据条数
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		return pages;
	}

	/**
	 * 按条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<E> searchDataByConditions(E e, String fieldName, Object value)
			throws Exception {
		return baseDao.queryDataByConditions(e, fieldName, value);
	}

	/**
	 * 分页按条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<E> searchDataByConditionsPaging(E e, String fieldName,
			String value, int pageNo, int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize; // 求出当前初始页最小索引值
		return baseDao.queryDataByConditionsPaging(e, fieldName, value,
				beginIndex, pageSize);
	}

	/**
	 * 分页排序 conditionMap：key条件字段，value条件值 SortField：虚按此字段排序
	 * orderingRules：排序方式，升序或者降序 pageNo：从几页开始取数据 beginIndex：取数据从某行起 maxSet：最多去几行
	 */
	@SuppressWarnings("unchecked")
	public List<E> SearchDataPaginationAndSorting(E e,
			Map<String, String> conditionMap, String SortField,
			String orderingRules, int pageNo, int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize; // 求出当前初始页最小索引值
		return this.baseDao.queryDataPaginationAndSorting(e, conditionMap,
				SortField, orderingRules, beginIndex, pageSize);
	}

	@SuppressWarnings("unchecked")
	public int countDataByConditionsFuzzy(E e,
			Map<String, String> conditionMap, int pageSize) throws Exception {
		int total = baseDao.countDataByConditionsFuzzy(e, conditionMap); // 获取总数据条数
		int pages = calculateTotalPage(total, pageSize);// 获取计算分页页数
		return pages;
	}

	@SuppressWarnings("unchecked")
	public List<E> SearchDataPaginationAndSortingAndFuzzy(E e,
			Map<String, String> conditionMap, String SortField,
			String orderingRules, int pageNo, int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize; // 求出当前初始页最小索引值
		return this.baseDao.SearchDataPaginationAndSortingAndFuzzy(e,
				conditionMap, SortField, orderingRules, beginIndex, pageSize);
	}

	/**
	 * 按条件增加
	 */
	@SuppressWarnings("unchecked")
	public boolean addConditions(E e, String field, String value)
			throws Exception {
		List<E> list = null;
		list = this.baseDao.queryDataByConditions(e, field, value);
		if (list.size() == 0) {
			this.baseDao.save(e);
			return true;
		}
		return false;
	}

	/**
	 * 按条件修改
	 */
	@SuppressWarnings("unchecked")
	public boolean modifyByConditions(E e, Map<String, String> modifyMap,
			Map<String, String> conditionMap, String conditionPair)
			throws Exception {
		return this.baseDao.modifyByConditions(e, modifyMap, conditionMap,
				conditionPair);
	}

	/**
	 * 按条件查询
	 */
	public List<?> searchObjectDataByConditions(Object e, String fieldName,
			Object value) throws Exception {
		return this.baseDao.queryDataByConditionsObject(e, fieldName, value);
	}
}
