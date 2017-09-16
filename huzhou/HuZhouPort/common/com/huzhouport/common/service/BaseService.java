package com.huzhouport.common.service;

import java.util.List;
import java.util.Map;

public interface BaseService<E> {
		
	//获取beginIndex到endIndex间的数据
	public List<E> queryByIndex(E e,int pageNo,int pageSize)throws Exception;
	
	//根据指定页面尺寸及指定实体类计算指定表的总页数
	public int queryTotalPage(E e,int pageSize) throws Exception;
	
	//增加
	public boolean add(E e)throws Exception;
	//唯一索引
	public boolean addConditions(E e,String field,String value)throws Exception;
	
	//修改
	public boolean modify(E e)throws Exception;
	//按条件修改某些字段
	public boolean modifyByConditions(E e,Map<String,String> modifyMap,Map<String,String> conditionMap,String conditionPair)throws Exception;
	
	//删除
	public boolean delete(E e)throws Exception;
	
	//查询全部数据 ，不分页
	public List<E> searchAllData(E e)throws Exception;
	
	//按条件查询表里面数据(无分页)
	public List<E> searchDataByConditions(E e,String fieldName,Object value) throws Exception;
    
	//按条件获取记录总数
	public int countDataByConditionsPaging(E e,String fieldName,String value,int pageSize) throws Exception;
	
	//按条件查询表里面数据(分页)
	public List<E> searchDataByConditionsPaging(E e,String fieldName,String value,int pageNo,int pageSize) throws Exception;

	//分页排序
	public List<E> SearchDataPaginationAndSorting(E e,Map<String,String> conditionMap,String SortField,
			String orderingRules, int pageNo, int pageSize) throws Exception;
    //按条件模糊查询总页数
	public int countDataByConditionsFuzzy(E e,Map<String,String> conditionMap, int pageSize) throws Exception;
    //按条件模糊,分页排序
	public List<E> SearchDataPaginationAndSortingAndFuzzy(E e,Map<String,String> conditionMap,String SortField,
			String orderingRules, int pageNo, int pageSize) throws Exception;
	
	//按条件查询表里面数据(无分页)
	public List<?> searchObjectDataByConditions(Object e,String fieldName,Object value) throws Exception;
}
