package com.huzhouport.common.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<E> {
	//获取记录总数
	public int countRecord(E e) throws Exception; 
	
	//获取startSet到maxSet段的数据
	public List<E> queryByIndex(E e,int startSet,int maxSet) throws Exception;
	
	//增加
	public void save(E e)throws Exception;
	
	//修改
	public void update(E e)throws Exception;
	
	//修改或保存
	public boolean saveorUpdate(E e)throws Exception;
	
	//按条件修改某些字段
	public boolean modifyByConditions(Object e,Map<String,String> modifyMap,Map<String,String> conditionMap,String conditionPair)throws Exception;
	
	//删除
	public void delete(E e)throws Exception;
	
	//查询全部数据 ，不分页
	public List<E> queryAllData(E e)throws Exception;
	
	//按条件查询表里面数据(无分页)
	public List<E> queryDataByConditions(E e,String fieldName,Object value) throws Exception;
    
	//按条件获取记录总数
	public int countDataByConditionsPaging(E e,String fieldName,String value) throws Exception;
	
	//按条件查询表里面数据(无分页)
	public List<E> queryDataByConditionsPaging(E e,String fieldName,String value,int startSet,int maxSet) throws Exception;

	public List<E> queryDataByManyConditions(String e, String fieldName,String[] list);
	
	//分页排序
	public List<E> queryDataPaginationAndSorting(E e,Map<String,String> conditionMap,String SortField,
			String orderingRules, int startSet, int maxSet) throws Exception;
	 //按条件模糊查询总页数
	public int countDataByConditionsFuzzy(E e,Map<String,String> conditionMap)throws Exception;
    //按条件模糊//分页排序
	public List<E> SearchDataPaginationAndSortingAndFuzzy(E e,Map<String,String> conditionMap,String SortField,
			String orderingRules, int startSet, int maxSet) throws Exception;
	
	 //按条件查询总页数
	public int countDataByConditions(E e,Map<String,String> conditionMap)throws Exception;
	
	/**********************传对象*****************************/
	public List<?> queryDataByConditionsObject(Object e,String fieldName,Object value) throws Exception;

	//public  Object getRefresh(Object obj);
	public void savaObject(Object e) throws Exception ;
	public void deleteObject(Object e) throws Exception ;
}
