package com.huzhouport.electric.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.electric.dao.ElectricVisaDao;
import com.huzhouport.electric.dao.PortDao;
import com.huzhouport.electric.model.ElectricVisa;
import com.huzhouport.electric.service.ElectricVisaService;

public class ElectricVisaServiceImpl extends BaseServiceImpl<ElectricVisa>
		implements ElectricVisaService {
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	private ElectricVisaDao electricVisaDao;
	private PortDao portDao;

	public void setPortDao(PortDao portDao) {
		this.portDao = portDao;
	}

	public void setElectricVisaDao(ElectricVisaDao electricVisaDao) {
		this.electricVisaDao = electricVisaDao;
	}

	public String addElectricVisaInfo(ElectricVisa electricVisa)
			throws Exception {
		Port port = new Port();
		List<Port> listp = null;
		port = electricVisa.getListp().get(0);
		listp = this.portDao.queryDataByConditions(port, "portName", port
				.getPortName());
		if (listp.size() <= 0) {
			this.portDao.save(port);
			electricVisa.setStartingPort(port.getPortID());
		} else {
			electricVisa.setStartingPort(listp.get(0).getPortID());
		}
		port = electricVisa.getListp().get(1);
		listp = this.portDao.queryDataByConditions(port, "portName", port
				.getPortName());
		if (listp.size() <= 0) {
			this.portDao.save(port);
			electricVisa.setArrivalPort(port.getPortID());
		} else {
			electricVisa.setArrivalPort(listp.get(0).getPortID());
		}

		this.electricVisaDao.addElectricVisaInfo(electricVisa);
		return null;
	}

	public Map<String, Integer> countPageElectricVisaInfo(
			ElectricVisa electricVisa, int pageSize) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int total = this.electricVisaDao.countPageElectricVisaInfo(
				electricVisa, qcs.QCS(electricVisa.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> searchElectricVisaInfo(ElectricVisa electricVisa,
			int pageNo, int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.electricVisaDao.searchElectricVisaInfo(electricVisa, qcs
				.QCS(electricVisa.getQueryCondition()), qcs
				.Sequence(electricVisa.getQueryCondition()), beginIndex,
				pageSize);
	}

	public List<?> seeElectricVisaID(ElectricVisa electricVisa)
			throws Exception {

		return this.electricVisaDao.seeElectricVisaID(electricVisa);
	}

	public int countPageElectricVisaInfoAD(ElectricVisa electricVisa,int pageSize) throws Exception {
		int total = this.electricVisaDao.countPageElectricVisaInfoAD(
				electricVisa, qcs
				.QCS(electricVisa.getQueryCondition()));
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		return pages;
	}

	public List<?> searchElectricVisaInfoAD(ElectricVisa electricVisa,int pageNo, int pageSize)
			throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.electricVisaDao.searchElectricVisaInfoAD(electricVisa, qcs
				.QCS(electricVisa.getQueryCondition()),
				"", beginIndex, pageSize);
	}
}
