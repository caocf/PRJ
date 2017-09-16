package com.huzhouport.electric.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.electric.model.RegularVisa;

public interface RegularVisaDao extends BaseDao<RegularVisa>{
	
	//增加
	public String addRegularVisaInfo(RegularVisa regularVisa) throws Exception;
	
	//按ID
	public List<?> seeRegularVisaID(RegularVisa regularVisa)throws Exception;
	
	
	//查询条数
	public int countPageRegularVisaInfo(RegularVisa regularVisa, String condition)throws Exception;
	
	//查询信息
	public List<?> searchRegularVisaInfo(RegularVisa regularVisa,String condition,String sequence,int startSet, int maxSet) throws Exception;

}
