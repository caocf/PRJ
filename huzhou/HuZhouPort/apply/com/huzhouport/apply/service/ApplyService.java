package com.huzhouport.apply.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.apply.model.Apply;
import com.huzhouport.common.service.BaseService;

public interface ApplyService extends BaseService<Apply>{
	
	//增加
	public String addApplyInfo(Apply apply) throws Exception;
	//查询信息
	public List<?> searchApplyInfo(Apply apply,int pageNo, int pageSize) throws Exception;
	
	//查询条数
	public Map<String,Integer> countPageApplyInfo(Apply apply,int pageSize)throws Exception;
	
	//按ID
	public List<?> seeApplyID(Apply apply)throws Exception;
}
