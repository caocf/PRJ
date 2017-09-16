package com.huzhouport.electric.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.electric.model.ElectricVisa;

public interface ElectricVisaDao extends BaseDao<ElectricVisa> {

	// 查询条数
	public int countPageElectricVisaInfo(ElectricVisa electricVisa,
			String condition) throws Exception;

	// 查询信息
	public List<?> searchElectricVisaInfo(ElectricVisa electricVisa,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception;

	// 增加
	public String addElectricVisaInfo(ElectricVisa electricVisa)
			throws Exception;

	// 按ID
	public List<?> seeElectricVisaID(ElectricVisa electricVisa)
			throws Exception;

	public int countPageElectricVisaInfoAD(ElectricVisa electricVisa,
			String condition) throws Exception;

	public List<?> searchElectricVisaInfoAD(ElectricVisa electricVisa,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception;
}
