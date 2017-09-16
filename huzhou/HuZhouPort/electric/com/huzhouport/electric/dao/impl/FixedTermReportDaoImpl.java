package com.huzhouport.electric.dao.impl;

import java.util.List;
import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.electric.dao.FixedTermReportDao;
import com.huzhouport.electric.model.FixedTermReport;

public class FixedTermReportDaoImpl extends BaseDaoImpl<FixedTermReport>
		implements FixedTermReportDao {

	public String addFixedTermReportInfo(FixedTermReport fixedTermReport)
			throws Exception {
		this.hibernateTemplate.save(fixedTermReport);
		return null;
	}

	public int countPageFixedTermReportInfo(FixedTermReport fixedTermReport,
			String condition) throws Exception {
		String hql = "select count(*) from FixedTermReport f ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		return this.countEForeignKey(fixedTermReport, hql);
	}

	public String deleteaddFixedTermReportInfo(FixedTermReport fixedTermReport)
			throws Exception {
		this.hibernateTemplate.delete(fixedTermReport);
		return null;
	}

	public List<?> searchFixedTermReportInfo(FixedTermReport fixedTermReport,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception {
		String hql = "  from FixedTermReport f ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(fixedTermReport, hql,
				startSet, maxSet);
		return list;
	}

	public List<?> findRegularVisaByIdCodition(FixedTermReport fixedTermReport)
			throws Exception {
		String hql = "from RegularVisa r where r.fixedID="
				+ fixedTermReport.getFixedTermID() + " and( r.mark like '%"
				+ fixedTermReport.getCondition() + "%' or r.cargoTypes like '%"
				+ fixedTermReport.getCondition() + "%' or r.ingTime like '%"
				+ fixedTermReport.getCondition() + "%' or r.uniti like '%"
				+ fixedTermReport.getCondition() + "%') order by r.reportTime desc";
		return this.hibernateTemplate.find(hql);
	}

	public String deleteaddRegularVisaInfo(FixedTermReport fixedTermReport)
			throws Exception {
		String hql="delete RegularVisa where fixedID="+fixedTermReport.getFixedTermID();
		this.hibernateTemplate.bulkUpdate(hql);
		return null;
	}
	//获取wbservice并增加
	public boolean AddFixItemByWb(List<FixedTermReport> list)throws Exception{
		Boolean check=true;
		for(int i=0;i<list.size();i++){
			FixedTermReport fixedTermReport=list.get(i);
			String hql="delete FixedTermReport fx where fx.shipName='"+fixedTermReport.getShipName()+"'";
			this.deleteData(fixedTermReport,hql);
			this.hibernateTemplate.save(fixedTermReport);
		}
		return check;
	}

}
