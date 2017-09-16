package com.huzhouport.apply.dao;

import java.util.List;

import com.huzhouport.apply.model.Apply;
import com.huzhouport.common.dao.BaseDao;

public interface ApplyDao extends BaseDao<Apply> {
	
	//查询条数
	public int countPageApplyInfo(Apply apply, String condition)throws Exception;
	
	//查询信息
	public List<?> searchApplyInfo(Apply apply,String condition,String sequence,int startSet, int maxSet) throws Exception;
	
	//增加
	public String addApplyInfo(Apply apply) throws Exception;
	
	//按ID
	public List<?> seeApplyID(Apply apply)throws Exception;


}
