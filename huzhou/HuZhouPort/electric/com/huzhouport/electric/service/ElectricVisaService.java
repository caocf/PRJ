package com.huzhouport.electric.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.BaseService;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.electric.model.ElectricVisa;

public interface ElectricVisaService extends BaseService<ElectricVisa>{
	
	//增加
	public String addElectricVisaInfo(ElectricVisa electricVisa) throws Exception;
	//查询信息
	public List<?> searchElectricVisaInfo(ElectricVisa electricVisa,int pageNo, int pageSize) throws Exception;
	
	//查询条数
	public Map<String,Integer> countPageElectricVisaInfo(ElectricVisa electricVisa,int pageSize)throws Exception;
	
	//按ID
	public List<?> seeElectricVisaID(ElectricVisa electricVisa)throws Exception;
	
	public int countPageElectricVisaInfoAD(ElectricVisa electricVisa, int pageSize) throws Exception;

	public List<?> searchElectricVisaInfoAD(ElectricVisa electricVisa, int pageNo, int pageSize)
			throws Exception;
}

