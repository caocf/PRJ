package com.huzhouport.electric.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.BaseService;
import com.huzhouport.electric.model.RegularVisa;

public interface RegularVisaService extends BaseService<RegularVisa>{
	
	// 增加
	public String addRegularVisaInfo(RegularVisa regularVisa)throws Exception;

	// 按ID
	public List<?> seeRegularVisaID(RegularVisa regularVisa)
			throws Exception;
	
	// 查询信息
	public List<?> searchRegularVisaInfo(RegularVisa regularVisa,
			int pageNo, int pageSize) throws Exception;

	// 查询条数
	public Map<String, Integer> countPageRegularVisaInfo(
			RegularVisa regularVisa, int pageSize) throws Exception;

}
