package com.huzhouport.electric.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.electric.dao.ElectricReportDao;
import com.huzhouport.electric.model.ElectricReport;

public class ElectricReportDaoImpl extends BaseDaoImpl<ElectricReport>
		implements ElectricReportDao {

	public String addElectricReportInfo(ElectricReport electricReport)
			throws Exception {
		this.hibernateTemplate.save(electricReport);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<?> seeElectricReportID(ElectricReport electricReport)
			throws Exception {
		String hql = "  from ElectricReport e where e.reportID='"
				+ electricReport.getReportID() + "'";
		return this.hibernateTemplate.find(hql);
	}

	public int countPageElectricReportInfo(ElectricReport electricReport,
			String condition) throws Exception {
		String hql = "select count(*) from ElectricReport e ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		return this.countEForeignKey(electricReport, hql);
	}

	public List<?> searchElectricReportInfo(ElectricReport electricReport,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception {
		String hql = "  from ElectricReport e ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(electricReport, hql,
				startSet, maxSet);
		return list;
	}

	public int countPageElectricReportInfoAD(ElectricReport electricReport,
			String condition) throws Exception {
		String hql = "select count(*) from ElectricReport e where (e.shipName like '%"
				+ electricReport.getCondtion()
				+ "%' or e.cargoType like '%"
				+ electricReport.getCondtion()
				+ "%' or e.estimatedTimeOfArrival like '%"
				+ electricReport.getCondtion()
				+ "%' or e.toBeDockedAtThePier like '%"
				+ electricReport.getCondtion() + "%') ";
		/*
		 * if (null != condition && condition != "") { hql += "and (" +
		 * condition + ")"; }
		 */
		return this.countEForeignKey(electricReport, hql);
	}

	public List<?> searchElectricReportInfoAD(ElectricReport electricReport,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception {
		String hql = " from ElectricReport e where (e.shipName like '%"
				+ electricReport.getCondtion() + "%' or e.cargoType like '%"
				+ electricReport.getCondtion()
				+ "%' or e.estimatedTimeOfArrival like '%"
				+ electricReport.getCondtion()
				+ "%' or e.toBeDockedAtThePier like '%"
				+ electricReport.getCondtion() + "%') ";
		// if (null != condition && condition != "") {
		// hql += " and (" + condition + ")";
		// }
		List<?> list = this.queryqueryEForeignKey(electricReport, hql
				+ " order by e.reportTime desc", startSet, maxSet);
		return list;

	}

	public int countPageElectricReportInfoPublic(ElectricReport electricReport,
			String condition) throws Exception {
		String hql = "select count(*) from ElectricReport e where (e.shipName like '%"
				+ electricReport.getCondtion()
				+ "%' or e.cargoType like '%"
				+ electricReport.getCondtion()
				+ "%' or e.estimatedTimeOfArrival like '%"
				+ electricReport.getCondtion()
				+ "%' or e.toBeDockedAtThePier like '%"
				+ electricReport.getCondtion() + "%') ";
		if (electricReport.getShipUser() != 0) {
			hql += " and e.shipUser=" + electricReport.getShipUser();
		}
		return this.countEForeignKey(electricReport, hql);
	}

	public List<?> searchElectricReportInfoPublic(
			ElectricReport electricReport, String condition, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = " from ElectricReport e where (e.shipName like '%"
				+ electricReport.getCondtion() + "%' or e.cargoType like '%"
				+ electricReport.getCondtion()
				+ "%' or e.estimatedTimeOfArrival like '%"
				+ electricReport.getCondtion()
				+ "%' or e.toBeDockedAtThePier like '%"
				+ electricReport.getCondtion() + "%') ";
		if (electricReport.getShipUser() != 0) {
			hql += " and e.shipUser=" + electricReport.getShipUser();
		}
		List<?> list = this.queryqueryEForeignKey(electricReport, hql
				+ " order by e.reportTime desc", startSet, maxSet);
		return list;
	}

}
